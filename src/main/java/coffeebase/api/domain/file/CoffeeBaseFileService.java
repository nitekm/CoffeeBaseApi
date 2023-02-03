package coffeebase.api.domain.file;

import coffeebase.api.exceptions.exception.FileLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CoffeeBaseFileService {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeBaseFileService.class);
    private final Path root = Paths.get("storage");
    private final CoffeeBaseFileRepository coffeeBaseFileRepository;

    public CoffeeBaseFileService(final CoffeeBaseFileRepository coffeeBaseFileRepository) {
        this.coffeeBaseFileRepository = coffeeBaseFileRepository;
    }

    public void createDir() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            logger.error("Unable to create directories for file storage");
        }
    }

    public CoffeeBaseFile save(MultipartFile file) {
        try {
            Path path = this.root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);

            var storedFile = new CoffeeBaseFile(file.getOriginalFilename(), path.toString());
            return coffeeBaseFileRepository.save(storedFile);
        } catch (Exception e) {
            logger.error("Error while saving image: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Resource load(String filename) {
        try {
            Path filePath = root.resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileLoadException("Could not read the file " + filename + "!");
            }
        } catch (MalformedURLException e) {
            logger.error("Error while loading image " + filename + "! Error: " + e.getMessage());
            throw new FileLoadException(e.getMessage());
        }
    }
}
