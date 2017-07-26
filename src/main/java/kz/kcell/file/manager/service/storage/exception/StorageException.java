package kz.kcell.file.manager.service.storage.exception;

/**
 * @author amotov
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
