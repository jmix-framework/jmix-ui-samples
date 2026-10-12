package io.jmix.uisamples.bean;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jmix.core.*;
import jakarta.annotation.PreDestroy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Component("uisamples_SamplesFileStorage")
public class SamplesFileStorage implements FileStorage {

    private static final Logger log = LoggerFactory.getLogger(SamplesFileStorage.class);

    public static final String DEFAULT_STORAGE_NAME = "uisamplesfs";

    protected String storageName;
    protected String storageDir;

    protected boolean isImmutableFileStorage;

    protected ExecutorService writeExecutor = Executors.newFixedThreadPool(5,
            new ThreadFactoryBuilder().setNameFormat("FileStorageWriter-%d").build());

    protected volatile Path[] storageRoots;

    @Autowired
    protected CoreProperties coreProperties;

    @Autowired
    protected TimeSource timeSource;

    public SamplesFileStorage() {
        this(DEFAULT_STORAGE_NAME);
    }

    public SamplesFileStorage(String storageName) {
        this.storageName = storageName;
    }

    public SamplesFileStorage(String storageName, String storageDir) {
        this(storageName);
        this.storageDir = storageDir;
    }

    @Override
    public String getStorageName() {
        return storageName;
    }

    protected Path[] getStorageRoots() {
        if (storageRoots == null) {
            String workDir = coreProperties.getWorkDir();
            Path dir = Paths.get(workDir, "filestorage");
            if (!dir.toFile().exists() && !dir.toFile().mkdirs()) {
                throw new FileStorageException(FileStorageException.Type.IO_EXCEPTION,
                        "Cannot create filestorage directory: " + dir.toAbsolutePath().toString());
            }
            storageRoots = new Path[]{dir};
        }
        return storageRoots;
    }

    public long saveStream(FileRef fileRef, InputStream inputStream) {
        Path[] roots = getStorageRoots();

        // Store to primary storage
        checkStorageDefined(roots, fileRef.getFileName());
        checkPrimaryStorageAccessible(roots, fileRef.getFileName());

        Path path = resolveWithinRoot(roots[0], fileRef);
        if (path == null) {
            throw new FileStorageException(FileStorageException.Type.IO_EXCEPTION,
                    "Invalid file path: " + fileRef.getPath());
        }
        Path parentPath = path.getParent();
        if (parentPath == null) {
            throw new FileStorageException(FileStorageException.Type.IO_EXCEPTION,
                    "Invalid storage root: " + path);
        }
        if (!parentPath.toFile().exists() && !parentPath.toFile().mkdirs()) {
            throw new FileStorageException(FileStorageException.Type.IO_EXCEPTION,
                    "Cannot create directory: " + parentPath.toAbsolutePath());
        }

        checkFileExists(path);

        long size;
        try (OutputStream outputStream = Files.newOutputStream(path, CREATE_NEW)) {
            size = IOUtils.copyLarge(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            FileUtils.deleteQuietly(path.toFile());
            throw new FileStorageException(FileStorageException.Type.IO_EXCEPTION, path.toAbsolutePath().toString(), e);
        }

        return size;
    }

    @Override
    public FileRef saveStream(String fileName, InputStream inputStream, Map<String, Object> parameters) {
        Map<String, String> fileRefParams = Maps.toMap(parameters.keySet(), key -> parameters.get(key).toString());
        FileRef fileRef = new FileRef(storageName, fileName, fileName, fileRefParams);
        saveStream(fileRef, inputStream);
        return fileRef;
    }

    @Override
    public InputStream openStream(FileRef reference) {

        Path[] roots = getStorageRoots();
        if (roots.length == 0) {
            log.error("No storage directories available");
            throw new FileStorageException(FileStorageException.Type.FILE_NOT_FOUND, reference.toString());
        }

        InputStream inputStream = null;
        for (Path root : roots) {
            Path path = resolveWithinRoot(root, reference);
            if (path == null) {
                continue;
            }

            if (!path.toFile().exists()) {
                log.error("File " + path + " not found");
                continue;
            }

            try {
                inputStream = Files.newInputStream(path);
            } catch (IOException e) {
                log.error("Error opening input stream for " + path, e);
            }
        }

        if (inputStream != null) {
            return inputStream;
        } else {
            throw new FileStorageException(FileStorageException.Type.FILE_NOT_FOUND, reference.toString());
        }
    }

    @Override
    public void removeFile(FileRef reference) {
        Path[] roots = getStorageRoots();
        if (roots.length == 0) {
            log.error("No storage directories defined");
            return;
        }

        for (Path root : roots) {
            Path filePath = resolveWithinRoot(root, reference);
            if (filePath == null) {
                continue;
            }
            File file = filePath.toFile();
            if (file.exists()) {
                if (!file.delete()) {
                    throw new FileStorageException(FileStorageException.Type.IO_EXCEPTION,
                            "Unable to delete file " + file.getAbsolutePath());
                }
            }
        }
    }

    @Override
    public boolean fileExists(FileRef reference) {
        Path[] roots = getStorageRoots();

        for (Path root : roots) {
            Path filePath = resolveWithinRoot(root, reference);
            if (filePath != null && filePath.toFile().exists()) {
                return true;
            }
        }
        return false;
    }

    protected void checkFileExists(Path path) {
        if (Files.exists(path) && isImmutableFileStorage) {
            throw new FileStorageException(FileStorageException.Type.FILE_ALREADY_EXISTS,
                    path.toAbsolutePath().toString());
        }
    }

    protected void checkPrimaryStorageAccessible(Path[] roots, String fileName) {
        if (!roots[0].toFile().exists() && !roots[0].toFile().mkdirs()) {
            log.error("Inaccessible primary storage at {}", roots[0]);
            throw new FileStorageException(FileStorageException.Type.STORAGE_INACCESSIBLE, fileName);
        }
    }

    protected void checkStorageDefined(Path[] roots, String fileName) {
        if (roots.length == 0) {
            log.error("No storage directories defined");
            throw new FileStorageException(FileStorageException.Type.STORAGE_INACCESSIBLE, fileName);
        }
    }

    @Nullable
    protected Path resolveWithinRoot(Path root, FileRef reference) {
        Path base = root.toAbsolutePath().normalize();
        Path path = base.resolve(reference.getPath()).normalize();

        if (!path.startsWith(base)) {
            log.warn("File reference {} points outside of the storage root", reference);
            return null;
        }

        return path;
    }

    /**
     * This method is mostly needed for compatibility with an old API.
     * <p>
     * If {@link #isImmutableFileStorage} is false then {@link #saveStream(FileRef, InputStream)}
     * will be overwriting existing files.
     */
    public void setImmutableFileStorage(boolean immutableFileStorage) {
        isImmutableFileStorage = immutableFileStorage;
    }

    @PreDestroy
    protected void stopWriteExecutor() {
        writeExecutor.shutdown();
    }
}
