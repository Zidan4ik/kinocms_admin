package com.example.kinocms_admin.util;

import com.example.kinocms_admin.enums.GalleriesType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static com.example.kinocms_admin.util.ImageUtil.deleteFile;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageUtilTest {
    private MockMultipartFile file;
    private static final String UPLOAD_DIR = "./test-files";
    private Path uploadPath;
    private Path tempDirectory;
    private Path tempFolder;
    private Path tempFile;

    @BeforeEach
    void setup() throws IOException {
        uploadPath = Paths.get(UPLOAD_DIR);
        file = new MockMultipartFile("file", "file.txt", "text/plain", "This is a test".getBytes());
        tempDirectory = Files.createTempDirectory(uploadPath, "testDir");
        tempFolder = Files.createDirectory(tempDirectory.resolve("folder1"));
        tempFile = Files.createFile(tempDirectory.resolve("folder1").resolve("testFile.txt"));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walkFileTree(tempDirectory, new SimpleFileVisitor<Path>() {
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

    @Test
    void shouldSaveAfterDelete_createsDirectoryIfNotExists() throws IOException {
        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            filesMock.when(() -> Files.exists(uploadPath)).thenReturn(false);
            ImageUtil.saveAfterDelete(UPLOAD_DIR, file, "testfile.txt");
        }
    }

    @Test
    void shouldSaveAfterDelete() throws IOException {
        ImageUtil.saveAfterDelete(tempFolder.toString(), file, file.getOriginalFilename());
    }

    @Test
    void shouldSaveAfterDelete_WhenThrowException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(IOException.class);
        try (MockedStatic<Files> mockedStatic = mockStatic(Files.class)) {
            mockedStatic.when(() -> Files.exists(Path.of("/path")))
                    .thenReturn(true);
            ImageUtil.saveAfterDelete("/path", file, "testfile.txt");
        }
    }

    @Test
    void shouldSaveAfterDelete_SuccessfulFileCopy() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "testfile.txt", "text/plain", "This is a test".getBytes());
        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            Path uploadPath = Paths.get("test/uploads");
            filesMock.when(() -> Files.exists(uploadPath)).thenReturn(false);
            filesMock.when(() -> Files.createDirectories(uploadPath)).thenReturn(uploadPath);
            filesMock.when(() -> Files.copy(any(InputStream.class), any(Path.class), eq(StandardCopyOption.REPLACE_EXISTING)))
                    .thenReturn(0L);
            ImageUtil.saveAfterDelete("test/uploads", file, "testfile.txt");
            filesMock.verify(() -> Files.createDirectories(uploadPath), times(1));
            filesMock.verify(() -> Files.copy(any(InputStream.class), eq(uploadPath.resolve("testfile.txt")), eq(StandardCopyOption.REPLACE_EXISTING)), times(1));
        }
    }

    @Test
    void shouldSavesAfterDelete_WhenSuccessfully() throws IOException {
        HashMap<String, MultipartFile> map = new HashMap<>();
        map.put("file1", file);
        ImageUtil.savesAfterDelete(UPLOAD_DIR, map);
    }

    @Test
    void shouldSavesAfterDelete_WhenFilesExistFalse() throws IOException {
        HashMap<String, MultipartFile> map = new HashMap<>();
        map.put("file1", file);
        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            filesMock.when(() -> Files.exists(uploadPath)).thenReturn(false);
            ImageUtil.savesAfterDelete(UPLOAD_DIR, map);
        }
    }

    @Test
    void shouldSavesAfterDelete_WhenThrowException() throws IOException {
        HashMap<String, MultipartFile> map = new HashMap<>();
        MultipartFile file = mock(MultipartFile.class);
        map.put("file1", file);
        when(file.getInputStream()).thenThrow(IOException.class);
        ImageUtil.savesAfterDelete(UPLOAD_DIR, map);
    }

    @Test
    void shouldDeleteFiles_WhenFileNotExist() {
        String upload = "/uploads/" + GalleriesType.films;
        ImageUtil.deleteFiles(upload, List.of("file"));
    }

    @Test
    void shouldDeleteFiles_WhenThrowException2() {
        Path tempDirectory = Path.of("folder1");
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(tempDirectory)).thenReturn(true);
            mockedFiles.when(() -> Files.walk(tempDirectory))
                    .thenThrow(IOException.class);
            ImageUtil.deleteFiles(tempDirectory.toString(), List.of("testFile.txt", "testFile2.txt"));
        }
    }

    @Test
    void shouldDeleteFiles_WhenFileExist() {
        Path tempDirectory = Path.of("folder1");
        Path testFilePath = tempDirectory.resolve("testFile.txt");
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(tempDirectory)).thenReturn(true);
            mockedFiles.when(() -> Files.walk(tempDirectory)).thenReturn(Stream.of(testFilePath));
            ImageUtil.deleteFiles(tempDirectory.toString(), List.of("testFile.txt", "testFile2.txt"));
        }
    }

    @Test
    void shouldDeleteFiles_WhenFilesNotContainFile() {
        Path tempDirectory = Path.of("folder1");
        Path testFilePath = tempDirectory.resolve("testFile.txt");
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(tempDirectory)).thenReturn(true);
            mockedFiles.when(() -> Files.walk(tempDirectory)).thenReturn(Stream.of(testFilePath));
            ImageUtil.deleteFiles(tempDirectory.toString(), List.of());
        }
    }

    @Test
    void shouldDeleteFiles_WhenThrowException() {
        Path tempDirectory = Path.of("folder1");
        Path testFilePath = tempDirectory.resolve("testFile.txt");
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(tempDirectory)).thenReturn(true);
            mockedFiles.when(() -> Files.walk(tempDirectory)).thenReturn(Stream.of(testFilePath));
            mockedFiles.when(() -> Files.delete(testFilePath))
                    .thenThrow(new IOException("Помилка при видаленні файлу"));
            ImageUtil.deleteFiles(tempDirectory.toString(), List.of("testFile.txt"));
            mockedFiles.verify(() -> Files.delete(testFilePath), times(1));
        }
    }

    @Test
    void shouldToReadHTMLFile_WhenThrowException() throws IOException {
        ImageUtil.toReadHTMLFile("file.html");
    }

    @Test
    public void testToReadHTMLFile_Success() throws IOException {
        Path tempFilePath = Files.createTempFile("test", ".html");
        String htmlContent = "<html><head><title>Test</title></head><body><h1>Test Content</h1></body></html>";
        Files.write(tempFilePath, htmlContent.getBytes());
        String result = ImageUtil.toReadHTMLFile(tempFilePath.toString());
        assertTrue(result.contains("<html>"));
        assertTrue(result.contains("<h1>Test Content</h1>"));
        Files.delete(tempFilePath);
    }

    @Test
    public void shouldReadHTMLFile_FileNotFound() throws IOException {
        String invalidPath = "nonexistent-file.html";
        ImageUtil.toReadHTMLFile(invalidPath);
    }

    @Test
    public void shouldDeleteFoldersByName_Success() throws IOException {
        ImageUtil.deleteFoldersByName(tempDirectory, "folder1");
        assertTrue(Files.notExists(tempDirectory.resolve("folder1")));
    }

    @Test
    public void shouldDeleteFile_WhenSuccessfully() {
        deleteFile(GalleriesType.films, 1L);
    }

    @Test
    public void shouldDeleteFile_WhenIOExceptionIsThrown() {
        GalleriesType type = GalleriesType.films;
        Long id = 1L;
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class);) {
            mockedFiles.when(() -> Files.walkFileTree(any(Path.class), any(SimpleFileVisitor.class)))
                    .thenThrow(new IOException());
            ImageUtil.deleteFile(type, id);
        }
    }

    @Test
    public void shouldNotDeleteFoldersByName_WhenFolderNameDoesNotMatch() throws IOException {
        Path basePath = Path.of("./uploads/films");
        String folderNameToDelete = "1";
        Path anotherDir = basePath.resolve("2");

        // Mocking Files.walkFileTree
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class);
             MockedStatic<ImageUtil> mockedImageUtil = mockStatic(ImageUtil.class)) {

            // Simulate walking the file tree and reaching a directory with a different name
            mockedFiles.when(() -> Files.walkFileTree(eq(basePath), any(SimpleFileVisitor.class)))
                    .thenAnswer(invocation -> {
                        SimpleFileVisitor<Path> visitor = invocation.getArgument(1);
                        // Simulate visiting directories
                        visitor.postVisitDirectory(anotherDir, null);
                        return null;
                    });

            // Test the method
            assertDoesNotThrow(() -> ImageUtil.deleteFoldersByName(basePath, folderNameToDelete));

            // Verify that deleteDirectory was not called for the wrong folder
            mockedImageUtil.verify(() -> ImageUtil.deleteDirectory(any()), times(0));
        }
    }

    @Test
    public void testGetFileByPath_FileFound() throws IOException {
        Path fileByPath = ImageUtil.getFileByPath(tempDirectory);
        assertNotNull(fileByPath);
        assertEquals(tempFile, fileByPath);
    }
}