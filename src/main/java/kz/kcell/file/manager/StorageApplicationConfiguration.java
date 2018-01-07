package kz.kcell.file.manager;

import kz.kcell.file.manager.service.storage.impl.FileSystemStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageApplicationConfiguration {

    @Bean
    public FileSystemStorageService readonlyStorageService(StorageApplicationProperties properties) {
        Path location = Paths.get(properties.getLocation().getReadonly());
        return new FileSystemStorageService(location);
    }

    @Bean
    public FileSystemStorageService writableStorageService(StorageApplicationProperties properties) {
        Path location = Paths.get(properties.getLocation().getWritable());
        return new FileSystemStorageService(location);
    }

}
