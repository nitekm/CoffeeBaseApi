package coffeebase.api.domain.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class GCPCoffeeBaseFileService implements CoffeeBaseFileService {

    @Override
    public CoffeeBaseFile save(MultipartFile file) {
        return null;
    }

    @Override
    public Resource load(String fileName) {
        return null;
    }
}
