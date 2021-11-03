package org.example.shop.controllers;

import org.example.shop.entity.Product;
import org.example.shop.service.PurchaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/purchases")
public class PurchaseHistoryController {
    PurchaseService purchaseService;

    public PurchaseHistoryController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("")
    public Map<Integer, String> getHistoryPurchaseDateList() {
        return purchaseService.getPurchaseDateList();
    }

    @GetMapping("/{id}")
    public List<Product> getHistoryPurchasedProducts(@PathVariable("id") Integer id) {
        return purchaseService.getHistoryPurchasedProducts(id);
    }
}
