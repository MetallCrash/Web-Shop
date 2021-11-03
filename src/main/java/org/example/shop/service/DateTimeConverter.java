package org.example.shop.service;

import org.example.shop.entity.Product;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String localDateTimeToString(Product product) {
        LocalDateTime dateTime = LocalDateTime.parse(product.getDateCreated().toString());
        return dateTime.format(FORMATTER);
    }

    public String localDateTimeToString(LocalDateTime localDateTimeForParsing) {
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeForParsing.toString());
        return localDateTime.format(FORMATTER);
    }

    public LocalDateTime stringToLocalDateTime(ResultSet resultSet) throws SQLException {
        return LocalDateTime.parse(resultSet.getString("date_created"), FORMATTER);
    }
}
