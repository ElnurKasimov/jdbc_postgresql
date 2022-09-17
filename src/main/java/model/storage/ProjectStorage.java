package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ProjectStorage implements Storage<ProjectDao> {

    public DatabaseManagerConnector manager;

    private final String GET_ALL_INFO = "SELECT * FROM project";
    private final String GET_COMPANY_PROJECTS =
     "SELECT * FROM project JOIN company ON project.company_id = company.company_id WHERE company_name LIKE ?";
    private final String GET_ID_BY_NAME =
     "SELECT project_id FROM project WHERE project_name LIKE ?";
    private final String INSERT_PROJECT_DEVELOPER =
     "INSERT INTO project_developer(project_id, developer_id) VALUES (?, ?)";





    public ProjectStorage (DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }



    @Override
    public ProjectDao save(ProjectDao entity) {
        return null;
    }

    @Override
    public Optional<ProjectDao> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ProjectDao> findByName(String name) {
        try(Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
           ProjectDao projectDao = mapProjectDao(resultSet);
            return Optional.ofNullable(projectDao);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<ProjectDao> findAll() {
        List<ProjectDao> projectDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
            ResultSet rs = connection.prepareStatement(GET_ALL_INFO).executeQuery()) {
                while (rs.next()) {
                    ProjectDao projectDao = new ProjectDao();
                    projectDao.setProject_id(rs.getLong("project_id"));
                    projectDao.setProject_name(rs.getString("project_name"));
                    projectDao.setCost(rs.getInt("cost"));
                    projectDao.setStart_date(java.sql.Date.valueOf(LocalDate.parse(rs.getString("start_date"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                }
            }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return projectDaoList;
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
    public ProjectDao update(ProjectDao entity) {
        return null;
    }

    @Override
    public void delete(ProjectDao entity) {
    }

    public List<ProjectDao> getCompanyProjects (String companyName) {
        List<ProjectDao> companyProjectList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_PROJECTS)) {
            statement.setString(1, companyName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ProjectDao projectDao = new ProjectDao();
                projectDao.setProject_id(rs.getLong("project_id"));
                projectDao.setProject_name(rs.getString("project_name"));
                projectDao.setCost(rs.getInt("cost"));
                projectDao.setStart_date(java.sql.Date.valueOf(LocalDate.parse(rs.getString("start_date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                companyProjectList.add(projectDao);
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return companyProjectList;
    }

    public Optional<Long> getIdByName (String name) {
        Optional<Long> id = Optional.empty();
        try (Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ID_BY_NAME)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = Optional.of(rs.getLong("project_id"));
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return id;
    }

    public void saveProjectDeveloperRelation(ProjectDao projectDao, DeveloperDao developerDao) {
        try (Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_PROJECT_DEVELOPER)){
            statement.setLong(1, projectDao.getProject_id());
            statement.setLong(2, developerDao.getDeveloper_id());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    private ProjectDao mapProjectDao(ResultSet resultSet) throws SQLException {
        ProjectDao projectDao = null;
        while (resultSet.next()) {
            projectDao = new ProjectDao();
            projectDao.setProject_id(resultSet.getLong("project_id"));
            projectDao.setCompanyDao(resultSet.getString("customer_name"));
            projectDao.setCustomerDao();
            projectDao.setReputation(CustomerDao.Reputation.valueOf(resultSet.getString("reputation")));
        }
        return customerDao;
    }
}
