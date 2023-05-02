package coffeebase.api.config;

import coffeebase.api.domain.file.CoffeeBaseFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {

    private final CoffeeBaseFileService fileService;

    @Override
    public void run(final ApplicationArguments args) {
        fileService.checkStorageLocation();
    }
}
