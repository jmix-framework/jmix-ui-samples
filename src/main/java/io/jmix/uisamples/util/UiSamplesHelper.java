/*
 * Copyright 2022 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.uisamples.util;

import io.jmix.core.CoreProperties;
import io.jmix.core.Resources;
import io.jmix.core.common.util.Dom4j;
import io.jmix.flowui.view.ViewInfo;
import io.jmix.uisamples.theme.ThemeManager;
import org.apache.commons.io.FileUtils;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component("uisamples_UiSamplesHelper")
public class UiSamplesHelper {

    /**
     * Placeholder usable inside menu XML {@code <file name="..."/>} entries to
     * point at the active theme's sample resources. Resolved by
     * {@link #getFileContent(String)} into
     * {@code META-INF/resources/themes/<theme.id>/samples}.
     *
     * <p>If the file is missing for the active theme, the source tab is simply
     * not shown — this is intentional, so menu entries can declare per-theme
     * styling without having to provide a copy for every theme.
     *
     * <p>Analogous to the {@code {contextPath}} placeholder resolved in
     * {@code SampleView}.
     */
    public static final String CURRENT_THEME_PLACEHOLDER = "{currentTheme}";

    /**
     * Marker for content that belongs to a single theme, written inside an XML or Java comment:
     * {@code theme-only:<themeId>} opens the block, {@code theme-only:<themeId>:end} closes it
     * (e.g. {@code <!-- theme-only:lumo --> ... <!-- theme-only:lumo:end -->}).
     *
     * <p>In the displayed source ({@link #stripForDisplay}): under the matching theme only the
     * marker lines are dropped (content stays); under any other theme the whole block is dropped.
     * In the live demo ({@link #findThemeHiddenComponentIds}): XML elements with an {@code id}
     * inside a foreign-theme block are hidden.
     */
    static final String THEME_ONLY_MARKER = "theme-only:";

    /** Suffix that turns a {@link #THEME_ONLY_MARKER} into its closing marker. */
    static final String MARKER_END_SUFFIX = ":end";

    /**
     * Marker for sample-app plumbing that runs at runtime but must never appear in the displayed
     * source (e.g. a {@code if (themeManager.getCurrentTheme() == ...)} guard). Drop a single line
     * with a trailing {@code sample-hide} comment, or a span with {@code sample-hide:start} /
     * {@code sample-hide:end}. Theme-independent; has no effect on the live demo.
     */
    static final String SAMPLE_HIDE_MARKER = "sample-hide";
    static final String SAMPLE_HIDE_START = "sample-hide:start";
    static final String SAMPLE_HIDE_END = "sample-hide:end";

    protected final Resources resources;
    protected final CoreProperties coreProperties;
    protected final ObjectProvider<ThemeManager> themeManagerProvider;

    public UiSamplesHelper(Resources resources,
                           CoreProperties coreProperties,
                           ObjectProvider<ThemeManager> themeManagerProvider) {
        this.resources = resources;
        this.coreProperties = coreProperties;
        this.themeManagerProvider = themeManagerProvider;
    }

    @Nullable
    public String getFileContent(String src) {
        String resolved = src.contains(CURRENT_THEME_PLACEHOLDER)
                ? src.replace(CURRENT_THEME_PLACEHOLDER, currentThemePrefix())
                : src;

        String resourceCandidate = resources.getResourceAsString(resolved);

        if (resourceCandidate == null) {
            try {
                File file = ResourceUtils.getFile(resolved);
                resourceCandidate = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            } catch (IOException e) {
                return null;
            }
        }

        return stripForDisplay(resourceCandidate, getCurrentThemeId());
    }

    /**
     * @return the id of the theme currently applied to this UI (e.g. {@code "lumo"} / {@code "aura"}).
     */
    public String getCurrentThemeId() {
        return themeManagerProvider.getObject().getCurrentTheme().getId();
    }

    private String currentThemePrefix() {
        return "META-INF/resources/themes/" + getCurrentThemeId() + "/samples";
    }

    /**
     * Removes theme-specific and plumbing markers (and the content that should not be shown under
     * {@code activeThemeId}) from source displayed in the sample's code tabs. See
     * {@link #THEME_ONLY_MARKER} and {@link #SAMPLE_HIDE_MARKER} for the marker contract. Files
     * without markers are returned unchanged.
     */
    @Nullable
    protected String stripForDisplay(@Nullable String content, String activeThemeId) {
        if (content == null
                || (!content.contains(THEME_ONLY_MARKER) && !content.contains(SAMPLE_HIDE_MARKER))) {
            return content;
        }

        StringBuilder sb = new StringBuilder(content.length());
        boolean hideBlock = false;
        String themeBlockId = null;
        boolean first = true;

        for (String line : content.split("\n", -1)) {
            if (line.contains(SAMPLE_HIDE_START)) {
                hideBlock = true;
                continue;
            }
            if (line.contains(SAMPLE_HIDE_END)) {
                hideBlock = false;
                continue;
            }
            if (hideBlock) {
                continue;
            }

            int markerIndex = line.indexOf(THEME_ONLY_MARKER);
            if (markerIndex >= 0) {
                String token = line.substring(markerIndex + THEME_ONLY_MARKER.length())
                        .replace("-->", "")
                        .trim();
                themeBlockId = token.endsWith(MARKER_END_SUFFIX) ? null : token;
                continue;
            }

            if (themeBlockId != null && !themeBlockId.equals(activeThemeId)) {
                continue;
            }
            if (line.contains(SAMPLE_HIDE_MARKER)) {
                continue;
            }

            if (!first) {
                sb.append("\n");
            }
            sb.append(line);
            first = false;
        }

        return sb.toString();
    }

    /**
     * Reads the given view descriptor and returns the ids of components declared inside
     * {@link #THEME_ONLY_MARKER} blocks of themes other than the active one. {@link #findThemeHiddenComponentIds}
     * is used by the sample container to hide such components from the live demo of the current theme.
     */
    public List<String> findThemeHiddenComponentIds(String descriptorPath) {
        String raw = resources.getResourceAsString(descriptorPath);
        if (raw == null) {
            return List.of();
        }
        return themeHiddenComponentIds(raw, getCurrentThemeId());
    }

    protected List<String> themeHiddenComponentIds(String descriptorXml, String activeThemeId) {
        List<String> ids = new ArrayList<>();
        Document document = Dom4j.readDocument(descriptorXml);
        collectForeignThemeIds(document.getRootElement(), activeThemeId, ids);
        return ids;
    }

    private void collectForeignThemeIds(Element element, String activeThemeId, List<String> result) {
        String foreignThemeId = null;
        for (Node node : element.content()) {
            if (node instanceof Comment comment) {
                int markerIndex = comment.getText().indexOf(THEME_ONLY_MARKER);
                if (markerIndex >= 0) {
                    String token = comment.getText().substring(markerIndex + THEME_ONLY_MARKER.length()).trim();
                    if (token.endsWith(MARKER_END_SUFFIX)) {
                        foreignThemeId = null;
                    } else {
                        foreignThemeId = token.equals(activeThemeId) ? null : token;
                    }
                }
            } else if (node instanceof Element child) {
                if (foreignThemeId != null) {
                    collectAllIds(child, result);
                } else {
                    collectForeignThemeIds(child, activeThemeId, result);
                }
            }
        }
    }

    private void collectAllIds(Element element, List<String> result) {
        String id = element.attributeValue("id");
        if (id != null && !id.isBlank()) {
            result.add(id);
        }
        for (Element child : element.elements()) {
            collectAllIds(child, result);
        }
    }

    public String packageToPath(String pkg) {
        return pkg.replaceAll("[.]", "/");
    }

    public String getFileName(String src) {
        Path p = Paths.get(src);
        return p.getFileName().toString();
    }

    @Nullable
    public String getFileExtension(String src) {
        String fileName = getFileName(src);
        int index = fileName.lastIndexOf(".");
        if (index >= 0) {
            return fileName.substring(index + 1);
        }
        return null;
    }

    @Nullable
    public String findMessagePack(ViewInfo info) {
        Class<?> controllerClass = info.getControllerClass();

        for (Locale locale : coreProperties.getAvailableLocales()) {
            String messagesFileName = String.format("messages_%s.properties", locale.toString());

            if (controllerClass.getResource(messagesFileName) != null) {
                return controllerClass.getPackage().getName();
            }
        }

        return null;
    }
}
