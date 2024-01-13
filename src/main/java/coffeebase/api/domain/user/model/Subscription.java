package coffeebase.api.domain.user.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Subscription {
    private String productId;
    private String purchaseToken;
//    private Boolean active;
    private String purchasedDate;
}
