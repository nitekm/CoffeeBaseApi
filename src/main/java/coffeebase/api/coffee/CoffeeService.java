package coffeebase.api.coffee;

import org.springframework.stereotype.Service;

@Service
public class CoffeeService {
    private CoffeeRepository repository;

    CoffeeService(final CoffeeRepository repository) {
        this.repository = repository;
    }

    void switchFavourite(int id) {
        var coffee = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        coffee.setFavourite(!coffee.isFavourite());
        repository.save(coffee);
        }
}
