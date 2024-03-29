package coffeebase.api.domain.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface CoffeeBaseFileService {

    void checkStorageLocation();
    CoffeeBaseFile save(MultipartFile file);
    Resource load(String fileName);

    void delete(String fileName);

    default String generateFileName(String originalFileName) {
        var timestamp = LocalDateTime.now();
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        var date = timestamp.format(dateTimeFormatter);

        return date + "_" + originalFileName;
    }
}
