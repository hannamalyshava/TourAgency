package org.example.bd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String PROPERTIES_FILE = "db.properties";

    static {
        try {
            // Явная загрузка драйвера PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL драйвер не найден!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) throw new IOException("Файл " + PROPERTIES_FILE + " не найден");
            props.load(input);
        } catch (IOException e) {
            throw new SQLException("Ошибка загрузки конфигурации БД", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException("Не все параметры подключения к БД указаны в " + PROPERTIES_FILE);
        }

        System.out.println("Подключение к БД: " + url);
        return DriverManager.getConnection(url, user, password);
    }
}
