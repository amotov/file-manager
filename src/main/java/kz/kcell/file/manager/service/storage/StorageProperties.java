package kz.kcell.file.manager.service.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author amotov
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "/home/amotov/Work/temp/";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
