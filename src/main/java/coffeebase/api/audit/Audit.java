package coffeebase.api.audit;


import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable
public class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void setCreatedOn() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void setUpdatedOn() {
        updatedOn = LocalDateTime.now();
    }
}
