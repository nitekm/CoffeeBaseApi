package coffeebase.api.domain.user.model;

public record SubscriptionDTO(
        String productId,
        String purchaseToken,
        boolean active,
        String purchasedDate
)
{}
