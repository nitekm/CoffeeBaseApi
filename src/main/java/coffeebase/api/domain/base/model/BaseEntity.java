package coffeebase.api.domain.base.model;

import coffeebase.api.security.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Data
@SuperBuilder
public abstract class BaseEntity<ID extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdByUserId;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        final User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        createdByUserId = loggedUser.getUserId();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        final User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        createdByUserId = loggedUser.getUserId();
    }
}
