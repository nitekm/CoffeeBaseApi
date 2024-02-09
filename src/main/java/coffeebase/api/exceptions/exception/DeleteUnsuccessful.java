package coffeebase.api.exceptions.exception;

import static coffeebase.api.domain.base.model.error.ErrorMessage.DELETE_FAILED;

public class DeleteUnsuccessful extends RuntimeException {
    public DeleteUnsuccessful() {
        super(DELETE_FAILED.getMessage());
    }
}
