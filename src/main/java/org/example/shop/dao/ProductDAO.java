package org.example.shop.dao;

import org.example.shop.service.DateTimeConverter;
import org.example.shop.entity.Product;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDAO {

    private static final String DATABASE = "jdbc:mysql://localhost:3306/shop_product";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private final DateTimeConverter dateTimeConverter;

    public ProductDAO(DateTimeConverter dateTimeConverter) {
        this.dateTimeConverter = dateTimeConverter;
    }

    public void createProduct(Product product) {
        final String sql = "INSERT INTO product (name,price,date_created) VALUES (?,?,?)";

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            String dateAsString = dateTimeConverter.localDateTimeToString(product);
            statement.setString(3, dateAsString);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Product> getProductList() {
        final String sql = "SELECT * FROM product";
        return getProducts(sql);
    }

    public List<Product> getProductListByPriceDesc() {
        final String sql = "SELECT * FROM product ORDER BY price DESC";
        return getProducts(sql);
    }

    public List<Product> getProductListByPriceAsc() {
        final String sql = "SELECT * FROM product ORDER BY price ASC";
        return getProducts(sql);
    }

    public List<Product> getProductListByDateCreatedDesc() {
        final String sql = "SELECT * FROM product ORDER BY date_created DESC";
        return getProducts(sql);
    }

    public List<Product> getProductListByDateCreatedAsc() {
        final String sql = "SELECT * FROM product ORDER BY date_created";
        return getProducts(sql);
    }

    public List<Product> getProductListInPriceRange(Integer lowerPriceLimit, Integer upperPriceLimit) {
        final String sql = "SELECT * FROM product WHERE price>? and price<?";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, lowerPriceLimit);
            statement.setInt(2, upperPriceLimit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                );
                LocalDateTime dateTime = dateTimeConverter.stringToLocalDateTime(resultSet);
                product.setDateCreated(dateTime);
                productList.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productList;
    }

    public Product findProductById(Integer id) {
        final String sql = "SELECT * FROM product WHERE id=?";

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDateTime dateTime = dateTimeConverter.stringToLocalDateTime(resultSet);
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"));
                product.setDateCreated(dateTimeConverter.stringToLocalDateTime(resultSet));
                return product;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void editProduct(Product product) {
        final String sql = "UPDATE product SET name=?, price=?, date_created=? WHERE id=?";

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            String dateAsString = dateTimeConverter.localDateTimeToString(product);
            statement.setString(3, dateAsString);
            statement.setInt(4, product.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeProduct(Integer id) {
        final String sql = "DELETE FROM product WHERE id=?";

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<Product> getProducts(String sql) {
        List<Product> productList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                );
                LocalDateTime dateTime = dateTimeConverter.stringToLocalDateTime(resultSet);
                product.setDateCreated(dateTime);
                productList.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productList;
    }
}
