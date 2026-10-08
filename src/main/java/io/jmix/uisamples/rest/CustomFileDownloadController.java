package io.jmix.uisamples.rest;

import io.jmix.core.FileRef;
import io.jmix.core.FileTransferException;
import io.jmix.core.FileTransferService;
import io.jmix.uisamples.bean.SamplesFileStorage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("uisamples_CustomFileDownloadController")
@RequestMapping("/custom-rest")
public class CustomFileDownloadController {

    protected FileTransferService fileTransferService;

    public CustomFileDownloadController(FileTransferService fileTransferService) {
        this.fileTransferService = fileTransferService;
    }

    @GetMapping(path = "/files")
    public void getFileRef(@RequestParam String fileRef,
                           HttpServletResponse response) {
        try {
            FileRef fileReference = FileRef.fromString(fileRef);
            if (!SamplesFileStorage.DEFAULT_STORAGE_NAME.equals(fileReference.getStorageName())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            fileTransferService.downloadAndWriteResponse(fileReference, fileReference.getStorageName(), false, response);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file reference", e);
        } catch (FileTransferException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage(), e);
        }
    }
}
