package org.example.shop.service;

import org.example.shop.dao.ProductDAO;
import org.example.shop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDAO productDAO;
    private final DateTimeConverter dateTimeConverter;

    public ProductService(ProductDAO productDAO, DateTimeConverter dateTimeConverter) {
        this.productDAO = productDAO;
        this.dateTimeConverter = dateTimeConverter;
    }

    public void addProduct(Product product) {
        productDAO.createProduct(product);
    }

    public List<Product> getProductList() {
        return productDAO.getProductList();
    }

    public void editProduct(Integer id, String name, Integer price) {
        Product product = getProductbyId(id);
        product.setName(name);
        product.setPrice(price);
        productDAO.editProduct(product);
    }

    public Product getProductbyId(Integer id) {
        Product product = productDAO.findProductById(id);
        return product;
    }

    public void removeProduct(Integer id) {
        productDAO.removeProduct(id);
    }

    public List<Product> getProductListByPriceDesc() {
        return productDAO.getProductListByPriceDesc();
    }

    public List<Product> getProductListByPriceAsc() {
        return productDAO.getProductListByPriceAsc();
    }

    public List<Product> getProductListInPriceRange(Integer lowerLimit, Integer upperLimit) {
        return productDAO.getProductListInPriceRange(lowerLimit, upperLimit);
    }

    public List<Product> getProductListByDateCreatedDesc() {
        return productDAO.getProductListByDateCreatedDesc();
    }

    public List<Product> getProductListByDateCreatedAsc() {
        return productDAO.getProductListByDateCreatedAsc();
    }

}
