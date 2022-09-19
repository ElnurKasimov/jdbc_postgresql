package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ProjectStorage implements Storage<ProjectDao> {

    public DatabaseManagerConnector manager;
    private CompanyStorage companyStorage;
    private CustomerStorage customerStorage;

    private final String GET_ALL_INFO = "SELECT * FROM project";
    private final String GET_COMPANY_PROJECTS =
     "SELECT * FROM project JOIN company ON project.company_id = company.company_id WHERE company_name LIKE ?";
    private final String GET_ID_BY_NAME =
     "SELECT project_id FROM project WHERE project_name LIKE ?";
    private final String INSERT_PROJECT_DEVELOPER =
     "INSERT INTO project_developer(project_id, developer_id) VALUES (?, ?)";
    private final String FIND_BY_NAME = "SELECT * FROM project WHERE project_name  LIKE  ?";
    private final String FIND_BY_ID = "SELECT * FROM project WHERE project_id = ?";
    private final String INSERT = "INSERT INTO project(project_name, company_id, customer_id, cost, start_date) VALUES (?, ?, ?, ?, ?)";



    public ProjectStorage (DatabaseManagerConnector manager, CompanyStorage companyStorage,
                                             CustomerStorage customerStorage) throws SQLException {
        this.manager = manager;
        this.companyStorage = companyStorage;
        this.customerStorage = customerStorage;
    }

    @Override
    public ProjectDao save(ProjectDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getProject_name());
            statement.setLong(2, entity.getCompanyDao().getCompany_id());
            statement.setLong(3, entity.getCustomerDao().getCustomer_id());
            statement.setInt(4, entity.getCost());
            statement.setDate(5,  entity.getStart_date());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setProject_id(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Project saving was interrupted, ID has not been obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("The project was not created");
        }
        return entity;
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
                    projectDaoList.add(projectDao);
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
                projectDao.setCompanyDao(companyStorage.findById(rs.getLong("company_id")).get());
                projectDao.setCustomerDao(customerStorage.findById(rs.getLong("customer_id")).get());
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
            projectDao.setProject_name(resultSet.getString("project_name"));
            CompanyDao companyDao = companyStorage.findById(resultSet.getLong("company-id")).get();
            projectDao.setCompanyDao(companyDao);
            CustomerDao customerDao = customerStorage.findById(resultSet.getLong("customer_id")).get();
            projectDao.setCustomerDao(customerDao);
            projectDao.setCost(resultSet.getInt("cost"));
            projectDao.setStart_date(java.sql.Date.valueOf(LocalDate.parse(resultSet.getString("start_date"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        }
        return projectDao;
    }
}
