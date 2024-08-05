package com.example.kinocms_admin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class ImageUtil {

    public static void saveAfterDelete(String uploadDir, MultipartFile multipartFile, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (Files.exists(uploadPath)) {
            List<Path> paths = Files.list(uploadPath).toList();
            for (Path p : paths) {
                Files.delete(p);
            }
        }
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void savesAfterDelete(String uploadDir, HashMap<String, MultipartFile> mapFiles) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (Map.Entry<String, MultipartFile> f : mapFiles.entrySet()) {
            try (InputStream inputStream = f.getValue().getInputStream()) {
                Path filePath = uploadPath.resolve(f.getKey());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFoldersByName(Path basePath, String folderNameToDelete) throws IOException {
        Files.walkFileTree(basePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (dir.getFileName() != null && Objects.equals(dir.getFileName().toString(), folderNameToDelete)) {
                    deleteDirectory(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void deleteDirectory(Path dir) throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void deleteFiles(String uploadDir, List<String> namesDelete) {
        Path uploadPath = Paths.get(uploadDir);
        if (Files.exists(uploadPath)) {
            try {
                Files.walk(uploadPath)
                        .map(Path::toFile)
                        .forEach(file -> {
                            if (namesDelete.contains(file.getName())) {
                                try {
                                    Files.delete(file.toPath());
                                } catch (IOException e) {
                                    System.err.println("Помилка при видаленні файлу: " + e.getMessage());
                                }
                            }
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
