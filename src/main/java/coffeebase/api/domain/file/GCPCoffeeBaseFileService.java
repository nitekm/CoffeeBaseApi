package coffeebase.api.domain.file;

import coffeebase.api.exceptions.exception.FileLoadException;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class GCPCoffeeBaseFileService implements CoffeeBaseFileService {

    @Value("${gcp.bucket.name}")
    private String BUCKET_NAME;

    private final CoffeeBaseFileRepository coffeeBaseFileRepository;
    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    @Override
    public void checkStorageLocation() {
        Bucket bucket = storage.get(BUCKET_NAME);
        if (bucket == null) {
            storage.create(BucketInfo.of(BUCKET_NAME));
            log.info("Created bucket for storage: " + BUCKET_NAME);
        } else {
            log.info("Bucket " + BUCKET_NAME + " already exists");
        }
    }

    @Override
    public CoffeeBaseFile save(MultipartFile file) {
        try {
            String fileName = generateFileName(file.getOriginalFilename());
            BlobId gcpFileId = BlobId.of(BUCKET_NAME, fileName);
            BlobInfo gcpFileInfo = BlobInfo.newBuilder(gcpFileId).build();
            storage.create(gcpFileInfo, file.getBytes());
            var storedFile = CoffeeBaseFile.builder()
                    .name(fileName)
                    .path(gcpFileId.toString())
                    .build();
            return coffeeBaseFileRepository.save(storedFile);

        } catch (IOException e) {
            log.error("Error while saving image to cloud storage" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource load(String fileName) {
        try {
            BlobId gcpFileId = BlobId.of(BUCKET_NAME, fileName);
            Blob gcpFile = storage.get(gcpFileId);
            return new ByteArrayResource(gcpFile.getContent());
        } catch (RuntimeException e) {
            throw new FileLoadException("Unable to load file " + fileName + " with cause " + e.getMessage());
        }
    }
}
