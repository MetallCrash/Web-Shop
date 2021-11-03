package org.example.shop.service;

import org.example.shop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ProductGeneratorService {
    private final List<String> productNouns = List.of("Meat", "Cheese", "Bread", "Cake", "Wine", "Coffee", "Chips",
            "Soda", "Spaghetti", "PopCorn");
    private final List<String> productAdjectives = List.of("Wonderful", "Delicious", "Awesome", "Tasty", "Fresh",
            "Rotten", "Bitter", "Dirty", "Large", "Small");
    private final Random random = new Random();

    public Product generateProduct() {
        int price = random.nextInt(100);
        String name = productAdjectives.get(random.nextInt(productAdjectives.size())) + " " +
                productNouns.get(random.nextInt(productNouns.size()));
        return new Product(name, price);
    }
}
