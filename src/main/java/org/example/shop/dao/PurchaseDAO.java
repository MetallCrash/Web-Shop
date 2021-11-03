package org.example.shop.dao;

import org.example.shop.entity.Product;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PurchaseDAO {

    private static final String DATABASE = "jdbc:mysql://localhost:3306/shop_product";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public void addPurchaseProduct(Product product, int purchaseId) {
        final String sql = "INSERT INTO purchase_product (purchase_id, name, price) VALUES (?,?,?)";

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, purchaseId);
            statement.setString(2, product.getName());
            statement.setInt(3, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Integer addPurchase(String dateTime) {
        final String sql = "INSERT INTO purchase (purchase_date) VALUE (?)";

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dateTime);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating purchase failed, no ID obtained.");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Map<Integer, String> getPurchaseDateList() {
        final String sql = "SELECT * FROM purchase";
        Map<Integer, String> purchaseDateList = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                purchaseDateList.put(resultSet.getInt("id"), resultSet.getString("purchase_date"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return purchaseDateList;
    }

    public List<Product> getHistoryPurchasedProducts(Integer id) {
        final String sql = "SELECT * FROM purchase_product WHERE purchase_id=?";
        List<Product> historyPurchasedProductList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                historyPurchasedProductList.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return historyPurchasedProductList;
    }
}
