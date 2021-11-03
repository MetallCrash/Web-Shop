package org.example.shop.controllers;

import org.example.shop.entity.Product;
import org.example.shop.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/new")
    public String newProduct(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "price", required = false) int price) {
        Product product = new Product(name, price);
        productService.addProduct(product);
        return product.toString();
    }

    @GetMapping("/del")
    public String removeProduct(@RequestParam(value = "id", required = false) Integer id) {
        Product productForRemoving = productService.getProductbyId(id);
        productService.removeProduct(id);
        return productForRemoving + "\nProduct was deleted";
    }

    @GetMapping("")
    public List<Product> getProductList() {
        return productService.getProductList();
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "price", required = false) Integer price) {
        Product productBeforeEditting = productService.getProductbyId(id);
        productService.editProduct(id, name, price);
        Product productAfterEditing = productService.getProductbyId(id);
        return "Продукт до: " + productBeforeEditting.toString() + "\n" +
                "Продукт после: " + productAfterEditing;
    }

    @GetMapping("/sort/price/asc")
    public List<Product> getProductListByAsc() {
        return productService.getProductListByPriceAsc();
    }

    @GetMapping("/sort/price/desc")
    public List<Product> getProductListByDesc() {
        return productService.getProductListByPriceDesc();
    }

    @GetMapping("/sort/price/limit")
    public List<Product> getProductListInPriceRange(@RequestParam(value = "lowerLimit", required = false) Integer lowerLimit,
                                                    @RequestParam(value = "upperLimit", required = false) Integer upperLimit) {
        return productService.getProductListInPriceRange(lowerLimit, upperLimit);
    }

    @GetMapping("/sort/date-created/asc")
    public List<Product> getProductListByDateCreatedAsc() {
        return productService.getProductListByDateCreatedAsc();
    }

    @GetMapping("/sort/date-created/desc")
    public List<Product> getProductListByDateCreatedDesc() {
        return productService.getProductListByDateCreatedDesc();
    }
}
