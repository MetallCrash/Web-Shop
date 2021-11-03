package org.example.shop.controllers;

import org.example.shop.service.emulation.PurchaseEmulationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emulating")
public class EmulatorController {
    private final PurchaseEmulationService purchaseEmulationService;

    public EmulatorController(PurchaseEmulationService purchaseEmulationService) {
        this.purchaseEmulationService = purchaseEmulationService;
    }

    @GetMapping()
    public List<String> getEmulatedProductList() {
        purchaseEmulationService.startEmulationProcess();
        return purchaseEmulationService.getEmulatedProductList();
    }
}
