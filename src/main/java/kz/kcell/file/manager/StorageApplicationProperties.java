package kz.kcell.file.manager;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author amotov
 */
@ConfigurationProperties("storage")
public class StorageApplicationProperties {

    private final Location location = new Location();

    public Location getLocation() {
        return location;
    }

    static class Location {

        private String readonly;
        private String writable;

        public String getReadonly() {
            return readonly;
        }

        public void setReadonly(String readonly) {
            this.readonly = readonly;
        }

        public String getWritable() {
            return writable;
        }

        public void setWritable(String writable) {
            this.writable = writable;
        }
    }

}
