package org.example.service;

import org.example.bd.DatabaseConnection;
import org.example.model.Program;
import org.example.repository.ProgramRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Сервис для работы с туристическими программами.
 * Каждый метод выполняет одну бизнес-операцию в рамках одной транзакции.
 */
public class ProgramService {

    // Добавить новую программу
    public void addProgram(Program program) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            ProgramRepository repository = new ProgramRepository(conn);
            repository.add(program);

            conn.commit(); // завершить транзакцию
        } catch (SQLException e) {
            throw new SQLException("Ошибка при добавлении программы", e);
        }
    }

    // Получить список всех программ
    public List<Program> getAllPrograms() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            ProgramRepository repository = new ProgramRepository(conn);
            List<Program> programs = repository.findAll();

            conn.commit();
            return programs;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении списка программ", e);
        }
    }

    // Найти программу по ID
    public Program findById(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            ProgramRepository repository = new ProgramRepository(conn);
            Program program = repository.findById(id);

            conn.commit();
            return program;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске программы по ID", e);
        }
    }

    // Найти программы по названию
    public List<Program> getProgramsByName(String name) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            ProgramRepository repository = new ProgramRepository(conn);
            List<Program> programs = repository.findByName(name);

            conn.commit();
            return programs;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске программ по названию", e);
        }
    }

    //  Удалить программу
    public void deleteProgram(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            ProgramRepository repository = new ProgramRepository(conn);
            repository.delete(id);

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Ошибка при удалении программы", e);
        }
    }
}
