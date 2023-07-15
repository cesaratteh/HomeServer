package com.eve.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigExtractor {

    private static final String CONFIG_RESOURCES_SOURCE_DIRECTORY_NAME =
            AppConfig.CONFIG_EXTRACTOR_RESOURCES_CONFIG_DIR_NAME;

    private static final Path CONFIG_DESTINATION_DIRECTORY =
            AppConfig.EXTERNAL_CONFIG_ABSOLUTE_PATH;

    public static void init() throws URISyntaxException, IOException {
        Set<String> fileNames = getConfigFiles("/" + CONFIG_RESOURCES_SOURCE_DIRECTORY_NAME, 1);
        Files.createDirectories(CONFIG_DESTINATION_DIRECTORY);
        copyFilesAndReplaceExisting(fileNames);
    }

    private static void copyFilesAndReplaceExisting(Set<String> configFiles) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        for (String file : configFiles) {
            InputStream resourceAsStream = loader.getResourceAsStream(CONFIG_RESOURCES_SOURCE_DIRECTORY_NAME + '/' + file);
            copyFile(resourceAsStream, file);
        }
    }

    private static void copyFile(InputStream file, String fileName) throws IOException {
        Files.copy(file,
                Paths.get(CONFIG_DESTINATION_DIRECTORY.toString(), fileName),
                StandardCopyOption.REPLACE_EXISTING);
    }

    private static Set<String> getConfigFiles(String folder, int maxDepth) throws URISyntaxException, IOException {
        Set<String> result;
        URI uri = ConfigExtractor.class.getResource(folder).toURI();
        boolean isJar = uri.getScheme().equals("jar");

        try (FileSystem fileSystem = isJar ? FileSystems.newFileSystem(uri, Collections.emptyMap()) : null) {
            Path myPath = isJar ? fileSystem.getPath(folder) : Paths.get(uri);

            result = Files.walk(myPath, maxDepth)
                    .filter(Files::isRegularFile)
                    .map(f -> f.getFileName().toString())
                    .collect(Collectors.toSet());
        }

        return result;
    }
}
