package coffeebase.api.exceptions.exception;

public class DeleteUnsuccessful extends RuntimeException {
    public DeleteUnsuccessful() {
        super("Delete operation failed");
    }
}
