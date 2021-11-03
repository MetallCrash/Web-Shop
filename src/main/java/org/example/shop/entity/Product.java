package org.example.shop.entity;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private int price;
    private LocalDateTime dateCreated;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
        dateCreated = LocalDateTime.now();
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
        dateCreated = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
