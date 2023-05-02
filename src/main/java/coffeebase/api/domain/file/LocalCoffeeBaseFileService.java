package coffeebase.api.domain.file;

import coffeebase.api.exceptions.exception.FileLoadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
public class LocalCoffeeBaseFileService implements CoffeeBaseFileService {
    private final Path root = Paths.get("storage").toAbsolutePath().normalize();

    private final CoffeeBaseFileRepository coffeeBaseFileRepository;

    @Override
    public void checkStorageLocation() {
        if (Files.exists(root)) {
            log.info("Storage directory on path " + root + " already exists");
        } else {
            try {
                Files.createDirectories(root);
                log.info("Created storage directory on " + root);
            } catch (IOException e) {
                log.error("Unable to create directories for file storage");
            }
        }
    }

    @Override
    public CoffeeBaseFile save(MultipartFile file) {
        try {
            String fileName = generateFileName(file.getOriginalFilename());
            Path path = this.root.resolve(fileName);
            Files.copy(file.getInputStream(), path);

            var storedFile = CoffeeBaseFile.builder()
                    .name(fileName)
                    .path(path.toString())
                    .build();
            return coffeeBaseFileRepository.save(storedFile);
        } catch (Exception e) {
            log.error("Error while saving image: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path filePath = root.resolve(filename).normalize();
            var urlResource = new UrlResource(filePath.toUri());

            if (urlResource.exists() && urlResource.isReadable()) {
                return urlResource;
            } else {
                throw new FileLoadException("Could not load file " + filename + " because it does not exists or is not readable");
            }
        } catch (IOException e) {
            throw new FileLoadException("Unable to load file " + filename + " with cause " + e.getMessage());
        }
    }
}
