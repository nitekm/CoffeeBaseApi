package coffeebase.api.domain.file;

import coffeebase.api.exceptions.exception.FileLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CoffeeBaseFileService {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeBaseFileService.class);
    private final Path root = Paths.get("storage").toAbsolutePath().normalize();
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
            String fileName = generateFileName(file.getOriginalFilename());
            Path path = this.root.resolve(fileName);
            Files.copy(file.getInputStream(), path);

            var storedFile = new CoffeeBaseFile(fileName, path.toString());
            return coffeeBaseFileRepository.save(storedFile);
        } catch (Exception e) {
            logger.error("Error while saving image: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Resource load(String filename) {

        try {
            Path filePath = root.resolve(filename).normalize();
            var urlResource = new UrlResource(filePath.toUri());

            if (urlResource.exists() && urlResource.isReadable()) {
                return urlResource;
            } else {
                throw new FileLoadException("Could not load file " + filename);
            }
        } catch (IOException e) {
            throw new FileLoadException("Unable to load file " + filename);
        }
    }

    private String generateFileName(String originalFileName) {
        var dateTimePattern = "yyyy-MM-dd'T'HH-mm-ss";
        var dateTimeFormat = new SimpleDateFormat(dateTimePattern);
        var date = dateTimeFormat.format(new Date());

        return originalFileName + "_" + date;
    }
}
