package coffeebase.api.domain.base.model.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    NOT_PERMITTED_TO_MODIFY("User is not permitted to modify given item"),
    BREW_NOT_FOUND("Brew with given ID not found"),
    COFFEE_NOT_FOUND("Coffee with given ID not found"),
    TAG_NOT_FOUND("Tag with given id not found"),
    NO_MATCHING_STRATEGY_FOR_BREW_ACTION("Couldn't process given action"),
    USER_NOT_FOUND("No user found with given user ID"),
    FILE_UNREADABLE("File is unreadable or does not exists"),
    COULD_NOT_LOAD_FILE("Could not load file"),
    COULD_NOT_DELETE_FILE("Could not delete file"),
    INVALID_TOKEN("Token not valid or has expired"),
    DELETE_FAILED("Delete operation failed");



    private final String message;
    ErrorMessage(String message) {
        this.message = message;
    }
}
