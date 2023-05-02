package coffeebase.api.domain.file;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
@Profile("local")
public class GCPCoffeeBaseFileService implements CoffeeBaseFileService {

    private static final String BUCKET_NAME = "coffeebase-file-storage";

    private final CoffeeBaseFileRepository coffeeBaseFileRepository;
    private final Storage storage;

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
        BlobId gcpFileId = BlobId.of(BUCKET_NAME, fileName);
        Blob gcpFile = storage.get(gcpFileId);
        return new ByteArrayResource(gcpFile.getContent());
    }
}
