package coffeebase.api.domain.base.model.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int httpStatus,
        String error,
        String message
) { }
