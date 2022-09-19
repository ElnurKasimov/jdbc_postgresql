package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.*;

import java.sql.*;


public class RelationStorage {
    public DatabaseManagerConnector manager;

    private final String SAVE_PROJECT_DEVELOPER =
            "INSERT INTO project_developer (project_id, developer_id) VALUES (?, ?)";

    private final String SAVE_DEVELOPER_SKILL =
            "INSERT INTO  developer_skill (developer_id, skill_id) VALUES (?, ?)";

    public RelationStorage(DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }


    public void saveProjectDeveloperRelation (ProjectDao projectDao, DeveloperDao developerDao) {
        ProjectDeveloperDao projectDeveloperDao = new ProjectDeveloperDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_PROJECT_DEVELOPER, Statement.RETURN_GENERATED_KEYS)){
            statement.setLong(1, projectDao.getProject_id());
            statement.setLong(2, developerDao.getDeveloper_id());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projectDeveloperDao.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Saving of  relation between project and developer was interrupted, ID has not been obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("The relation between project and developer was not created");
        }
    }

    public void saveDeveloperSkillRelation (DeveloperDao developerDao, SkillDao skillDao) {
        DeveloperSkillDao DeveloperSkillDao = new DeveloperSkillDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_DEVELOPER_SKILL, Statement.RETURN_GENERATED_KEYS)){
            statement.setLong(1, developerDao.getDeveloper_id());
            statement.setLong(2, skillDao.getSkill_id());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    DeveloperSkillDao.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Saving of  relation between developer and skill  was interrupted, ID has not been obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("The relation between developer and skill was not created");
        }
    }


}
