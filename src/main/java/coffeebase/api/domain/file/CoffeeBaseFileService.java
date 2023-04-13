package coffeebase.api.domain.file;

import coffeebase.api.exceptions.exception.FileLoadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class CoffeeBaseFileService {
    private final Path root = Paths.get("storage").toAbsolutePath().normalize();

    private final CoffeeBaseFileRepository coffeeBaseFileRepository;

    public void createDir() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            log.error("Unable to create directories for file storage");
        }
    }

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

        return date + "_" + originalFileName;
    }
}
