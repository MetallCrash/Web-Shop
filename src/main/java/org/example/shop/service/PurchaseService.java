package org.example.shop.service;

import org.example.shop.dao.ProductDAO;
import org.example.shop.dao.PurchaseDAO;
import org.example.shop.entity.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {
    private final List<Product> purchaseList = new ArrayList<>();

    private final DateTimeConverter dateTimeConverter;
    private final PurchaseDAO purchaseDAO;
    private final ProductDAO productDAO;
    private final ProductService productService;

    public PurchaseService(DateTimeConverter dateTimeConverter, PurchaseDAO purchaseDAO, ProductDAO productDAO,
                           ProductService productService) {
        this.dateTimeConverter = dateTimeConverter;
        this.purchaseDAO = purchaseDAO;
        this.productDAO = productDAO;
        this.productService = productService;
    }

    public List<Product> getPurchaseList() {
        return purchaseList;
    }

    public void addProductForPurchase(Integer productId) {
        purchaseList.add(productService.getProductbyId(productId));
    }

    public void buyProducts() {
        String dateTime = dateTimeConverter.localDateTimeToString(LocalDateTime.now());
        int purchaseId = purchaseDAO.addPurchase(dateTime);

        purchaseList.forEach(product -> {
            purchaseDAO.addPurchaseProduct(product, purchaseId);
            productDAO.removeProduct(product.getId());
        });
        purchaseList.clear();
    }

    public Map<Integer, String> getPurchaseDateList() {
        return purchaseDAO.getPurchaseDateList();
    }

    public List<Product> getHistoryPurchasedProducts(Integer id) {
        return purchaseDAO.getHistoryPurchasedProducts(id);
    }
}
