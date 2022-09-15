package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import model.dao.SkillDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillStorage implements Storage<SkillDao> {

    public DatabaseManagerConnector manager;

    private final String GET_ALL_INFO = "SELECT * FROM project";
    private final String GET_COMPANY_PROJECTS =
     "SELECT * FROM project JOIN company ON project.company_id = company.company_id WHERE company_name LIKE ?";
    private final String GET_ID_BY_NAME =
     "SELECT project_id FROM project WHERE project_name LIKE ?";
    private final String INSERT_PROJECT_DEVELOPER =
     "INSERT INTO project_developer(project_id, developer_id) VALUES (?, ?)";





    public SkillStorage(DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }



    @Override
    public SkillDao save(SkillDao entity) {
        return null;
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



        getIdSkillByLanguageAndLevelSt.setString( 1, "%" + language + "%");
        getIdSkillByLanguageAndLevelSt.setString( 2, "%" + level + "%");
        long skillId;
        try(ResultSet rs = getIdSkillByLanguageAndLevelSt.executeQuery()) {
            rs.next();
            skillId = rs.getLong("skill_id");
        }
        Optional<Long> id = Optional.empty();
        return id;
    }

}
