package org.example.shop.controllers;

import org.example.shop.entity.Product;
import org.example.shop.service.PurchaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("")
    public List<Product> getProductList() {
        return purchaseService.getPurchaseList();
    }

    @GetMapping("/add/{id}")
    public String addProductForPurchase(@PathVariable("id") Integer id) {
        purchaseService.addProductForPurchase(id);
        return "The product was successfully added to purchase list.";
    }

    @GetMapping("/buy")
    public String buyProducts() {
        purchaseService.buyProducts();
        return "Products have been successfully purchased";
    }
}
