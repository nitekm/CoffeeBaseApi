package coffeebase.api.config;

import coffeebase.api.domain.file.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {

    @Autowired
    private CoffeeBaseFileRepository coffeeBaseFileRepository;

    @Bean
    @ConditionalOnProperty(name = "fileService", havingValue = "local")
    CoffeeBaseFileService localCoffeeBaseFileService() {
        return new LocalCoffeeBaseFileService(coffeeBaseFileRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "fileService", havingValue = "gcp")
    CoffeeBaseFileService gcpCoffeeBaseFileService() {
        return new GCPCoffeeBaseFileService(coffeeBaseFileRepository);
    }
}
