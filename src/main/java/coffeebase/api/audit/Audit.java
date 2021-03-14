package coffeebase.api.audit;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
