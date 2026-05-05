package org.example.repository;

import org.example.model.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramRepository extends BaseRepository {

    public ProgramRepository(Connection connection) {
        setConnection(connection);
    }

    // Добавить новую программу
    public void add(Program program) throws SQLException {
        String sql = """
                INSERT INTO "programs" ("name", "description", "duration")
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, program.getName());
            ps.setString(2, program.getDescription());
            if (program.getDuration() != null) {
                ps.setInt(3, program.getDuration());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    // Найти все программы
    public List<Program> findAll() throws SQLException {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM \"programs\" ORDER BY \"program_id\"";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                programs.add(mapRow(rs));
            }
        }
        return programs;
    }

    //  Найти программу по ID
    public Program findById(Long id) throws SQLException {
        String sql = "SELECT * FROM \"programs\" WHERE \"program_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    //Найти программы по названию (поиск по подстроке, без учёта регистра)
    public List<Program> findByName(String name) throws SQLException {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM \"programs\" WHERE LOWER(\"name\") LIKE LOWER(?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    programs.add(mapRow(rs));
                }
            }
        }
        return programs;
    }

    // Обновить программу
    public void update(Program program) throws SQLException {
        String sql = """
                UPDATE "programs"
                SET "name" = ?, "description" = ?, "duration" = ?
                WHERE "program_id" = ?
                """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, program.getName());
            ps.setString(2, program.getDescription());
            if (program.getDuration() != null) {
                ps.setInt(3, program.getDuration());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            ps.setLong(4, program.getProgramId());
            ps.executeUpdate();
        }
    }

    // Удалить программу
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"programs\" WHERE \"program_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    //ResultSet → Program
    private Program mapRow(ResultSet rs) throws SQLException {
        Program program = new Program();
        program.setProgramId(rs.getLong("program_id"));
        program.setName(rs.getString("name"));
        program.setDescription(rs.getString("description"));
        Integer dur = rs.getInt("duration");
        if (!rs.wasNull()) {
            program.setDuration(dur);
        }
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            program.setCreatedAt(ts.toLocalDateTime());
        }
        return program;
    }
}
