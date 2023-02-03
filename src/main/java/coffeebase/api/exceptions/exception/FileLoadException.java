package coffeebase.api.exceptions.exception;

public class FileLoadException extends RuntimeException {
    public FileLoadException() {}

    public FileLoadException(final String message) {
        super(message);
    }
}
