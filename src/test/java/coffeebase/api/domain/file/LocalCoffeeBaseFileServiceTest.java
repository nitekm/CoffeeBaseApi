package coffeebase.api.domain.file;

import coffeebase.api.exceptions.exception.FileLoadException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@Import({LocalCoffeeBaseFileService.class})
class LocalCoffeeBaseFileServiceTest {

    @Autowired
    private LocalCoffeeBaseFileService localCoffeeBaseFileService;

    @MockBean
    private CoffeeBaseFileRepository coffeeBaseFileRepository;

    @BeforeEach
    void setup() throws IOException, NoSuchFieldException, IllegalAccessException {
        var root = LocalCoffeeBaseFileService.class.getDeclaredField("root");
        root.setAccessible(true);
        root.set(localCoffeeBaseFileService, Paths.get("test-dir").toAbsolutePath().normalize());
        Files.createDirectories(Paths.get("test-dir").toAbsolutePath().normalize());
        var file = CoffeeBaseFile.builder()
                .name("fileName")
                .path("path")
                .build();
        when(coffeeBaseFileRepository.save(any())).thenReturn(file);
    }

    @AfterEach
    void finish() throws IOException {
        var testDir = Paths.get("test-dir").toAbsolutePath().normalize();
        Files.walk(testDir)
                .sorted(java.util.Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    @DisplayName("Should save passed MultiPartFile and return CoffeeBaseFile")
    void givenMultiPartFile_whenSave_returnCoffeeBaseFile() {
        //given
        final var file = new MockMultipartFile("fileName", "fileName".getBytes());

        //when
        final var savedFile = localCoffeeBaseFileService.save(file);

        //then
        assertNotNull(savedFile);
    }

    @Test
    @DisplayName("Should throw exception when file is corrupted")
    public void givenCorruptedFile_whenSave_thenThrowException() {
        //given
        final var file = new MockMultipartFile("fileName", "fileName".getBytes()) {
            @Override
            public InputStream getInputStream() throws IOException {
                throw new IOException();
            }
        };

        //when
        //then
        assertThrows(
                RuntimeException.class,
                () -> localCoffeeBaseFileService.save(file)
        );
    }

    @Test
    public void givenExistingFile_whenLoad_thenReturnsUrlResource() throws IOException {
        // Given
        Path file = Paths.get("test-dir", "file.txt");
        Files.write(file, "test content".getBytes());

        // When
        Resource resource = localCoffeeBaseFileService.load("file.txt");

        // Then
        assertTrue(resource instanceof UrlResource);
        assertTrue(resource.exists());
        assertTrue(resource.isReadable());
    }

    @Test
    @DisplayName("Should throw exception given non existent file")
    public void givenNonExistentFile_whenLoad_thenThrowsFileLoadException() {
        // Given
        String filename = "non-existent.txt";

        // When, Then
        assertThrows(
                FileLoadException.class,
                () -> localCoffeeBaseFileService.load(filename)
        );
    }
}
