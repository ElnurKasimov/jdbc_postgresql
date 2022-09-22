package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.*;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;

import java.sql.*;
import java.util.Set;


public class RelationStorage {
    public DatabaseManagerConnector manager;

    public RelationStorage(DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }

    public void saveProjectDeveloperRelation(Set<ProjectDao> projectsDao, DeveloperDao developerDao) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO project_developer (project_id, developer_id)  VALUES ");
        for (ProjectDao projectDao : projectsDao) {
            insertSql.append("(?,?),");
        }
        insertSql.deleteCharAt(insertSql.length() - 1);
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql.toString())) {
            int index = 1;
            for (ProjectDao projectDao : projectsDao) {
                statement.setLong(index++, projectDao.getProject_id());
                statement.setLong(index++, developerDao.getDeveloper_id());
            }
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Relation project_developer not created");
        }
    }

    public void saveDeveloperSkillRelation(DeveloperDao developerDao, Set<SkillDao> skillsDao) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO developer_skill (developer_id, skill_id)  VALUES ");
        for (SkillDao skillDao : skillsDao) {
            insertSql.append("(?,?),");
        }
        insertSql.deleteCharAt(insertSql.length() - 1);
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql.toString())) {
            int index = 1;
            for (SkillDao skillDao : skillsDao) {
                statement.setLong(index++, developerDao.getDeveloper_id());
                statement.setLong(index++, skillDao.getSkill_id());
            }
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Relation project_developer not created");
        }
    }

}
