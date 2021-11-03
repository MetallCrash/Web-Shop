package org.example.shop.service.emulation;

import org.example.shop.entity.Product;
import org.example.shop.service.ProductGeneratorService;
import org.example.shop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PurchaseEmulationService {
    private final ProductGeneratorService productGenerator;
    private final ProductService productService;
    private final List<String> emulatedProductList = new ArrayList<>();

    public PurchaseEmulationService(ProductGeneratorService productGenerator, ProductService productService) {
        this.productGenerator = productGenerator;
        this.productService = productService;
    }

    public List<String> getEmulatedProductList() {
        return emulatedProductList;
    }



    public void startEmulationProcess() {
        emulatedProductList.clear();
        final Producer producer = new Producer(this);
        final Consumer consumer = new Consumer(this);
        Thread threadProducer = new Thread(producer, "Thread-producer");
        Thread threadConsumer = new Thread(consumer, "Thread-consumer");

        threadProducer.start();
        threadConsumer.start();
        try {
            threadProducer.join();
            threadConsumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void produceProduct() {
        while (productService.getProductList().size() > 10) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (productService) {
            Product product = productGenerator.generateProduct();
            productService.addProduct(product);
            emulatedProductList.add("Producer made: " + product.getName() + " with price: " + product.getPrice());
        }
        notify();
    }

    public synchronized void buyProduct() {
        Random random = new Random();
        int randomProduct = random.nextInt(productService.getProductList().size());
        while (productService.getProductList().size() < 5) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (productService) {
            List<Product> productList = productService.getProductList();
            Product product = new Product(productList.get(randomProduct).getName(), productList.get(randomProduct).getPrice());
            emulatedProductList.add("Consumer bought: " + product.getName() + " for: " + product.getPrice());
            productService.removeProduct(productList.get(randomProduct).getId());
        }
        notify();
    }
}
