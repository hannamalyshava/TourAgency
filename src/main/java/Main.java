import org.example.bd.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Проверка подключения к базе данных...");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println(" Подключение успешно установлено!");
                System.out.println("URL: " + connection.getMetaData().getURL());
                System.out.println("Пользователь: " + connection.getMetaData().getUserName());
            } else {
                System.out.println(" Не удалось установить подключение!");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к БД:");
            e.printStackTrace();
        }
    }
}
