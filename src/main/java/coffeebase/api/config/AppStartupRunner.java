package coffeebase.api.config;

import coffeebase.api.domain.file.LocalCoffeeBaseFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    private LocalCoffeeBaseFileService fileService;

    @Override
    public void run(final ApplicationArguments args) {
        logger.info("Creating storage directory on application startup");
        fileService.createDir();
    }
}
