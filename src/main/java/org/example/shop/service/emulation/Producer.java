package org.example.shop.service.emulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class Producer implements Runnable {
    private final PurchaseEmulationService purchaseEmulationService;

    @Autowired
    public Producer(PurchaseEmulationService purchaseEmulationService) {
        this.purchaseEmulationService = purchaseEmulationService;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                purchaseEmulationService.produceProduct();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
