package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import model.dao.SkillDao;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillStorage implements Storage<SkillDao> {

    public DatabaseManagerConnector manager;

    private final String GET_ID_BY_LANGUAGE_NAME = "SELECT skill_id FROM skills WHERE language LIKE ? AND level LIKE ?";
    private final String INSERT = "INSERT INTO skill(language, level) VALUES (?, ?)";


    public SkillStorage(DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }

    @Override
    public SkillDao save(SkillDao entity) {
        try (Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getLanguage());
            statement.setString(2, entity.getLevel());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setSkill_id(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Skill saving was interrupted, ID has not been obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("The skill was not created");
        }
        return entity;
    }

    @Override
    public Optional<SkillDao> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<SkillDao> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<SkillDao> findAll() {
        return null;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public boolean isExist(String name) {
        return false;
    }

    @Override
    public SkillDao update(SkillDao entity) {
        return null;
    }

    @Override
    public void delete(SkillDao entity) {
    }

    public Optional<Long> getIdSkillByLanguageAndLevel(String language, String level) {
        try(Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ID_BY_LANGUAGE_NAME)) {
            statement.setString( 1, "%" + language + "%");
            statement.setString( 2, "%" + level + "%");
            ResultSet resultSet = statement.executeQuery();
            long id = resultSet.getLong("skill_id");
            return Optional.ofNullable(id);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

}
