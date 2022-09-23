package model.storage;

import lombok.Data;
import lombok.Getter;
import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class DeveloperStorage implements Storage<DeveloperDao>{
    private DatabaseManagerConnector manager;
    private CompanyStorage companyStorage;
    private SkillStorage skillStorage;

    private final String GET_ALL_INFO = "SELECT * FROM developer";
    private final String FIND_BY_NAME = "SELECT * FROM developer WHERE lastName LIKE ? and firstName LIKE ? ";
    private final String INSERT = "INSERT INTO developer(lastName, firstName, age, company_id, salary) VALUES (?, ?, ?, ?, ?)";


    public DeveloperStorage (DatabaseManagerConnector manager, CompanyStorage companyStorage,
                             SkillStorage skillStorage) {
        this.manager = manager;
        this.companyStorage = companyStorage;
        this.skillStorage = skillStorage;
    }

    @Override
    public DeveloperDao save(DeveloperDao entity) {
        try (Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setInt(3, entity.getAge());
            statement.setLong(4, entity.getCompanyDao().getCompany_id());
            statement.setInt(5, entity.getSalary());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setDeveloper_id(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Developer saving was interrupted, ID has not been obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("The developer was not created");
        }
        return entity;
    }

    @Override
    public Optional<DeveloperDao> findById(long id) {
        return null;
    }

   @Override
   public Optional<DeveloperDao> findByName(String name) {
       return null;
   }


    public Optional<DeveloperDao> findByName(String lastName, String firstName) {
        try(Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            DeveloperDao developerDao = mapDeveloperDao(resultSet);
            return Optional.ofNullable(developerDao);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Optional<DeveloperDao>> findAll() {
        List<Optional<DeveloperDao>> developerDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
            ResultSet rs = connection.prepareStatement(GET_ALL_INFO).executeQuery()) {
                while (rs.next()) {
                    DeveloperDao developerDao = new DeveloperDao();
                    developerDao.setDeveloper_id(rs.getLong("developer_id"));
                    developerDao.setLastName(rs.getString("lastName"));
                    if (rs.getString("firstName") != null) {
                        developerDao.setFirstName(rs.getString("firstName"));
                    }
                    developerDao.setAge(rs.getInt("age"));
                    developerDao.setCompanyDao(companyStorage.findById(rs.getInt("company_id")).get());
                    developerDao.setSalary(rs.getInt("salary"));
                    developerDaoList.add(Optional.ofNullable(developerDao));
                }
            }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    return developerDaoList;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    public boolean isExist(String lastName, String firstName) {
        return findByName(lastName, firstName).isPresent();
    }

    @Override
    public boolean isExist(String name) {
        return false;
    }

    @Override
    public DeveloperDao update(DeveloperDao entity) {
        return null;
    }

    @Override
    public void delete(DeveloperDao entity) {
    }

    private DeveloperDao mapDeveloperDao(ResultSet resultSet) throws SQLException {
        DeveloperDao developerDao = null;
        while (resultSet.next()) {
            developerDao = new DeveloperDao();
            developerDao.setDeveloper_id(resultSet.getLong("developer_id"));
            developerDao.setLastName(resultSet.getString("lastName"));
            developerDao.setFirstName(resultSet.getString("firstName"));
            developerDao.setAge(resultSet.getInt("age"));
            developerDao.setSalary(resultSet.getInt("salary"));
            developerDao.setCompanyDao(companyStorage.findById(resultSet.getLong("company_id")).get());
        }
        return developerDao;
    }

}
