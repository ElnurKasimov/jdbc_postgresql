package model.storage;

import lombok.Data;
import lombok.Getter;
import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class DeveloperStorage implements Storage<DeveloperDao>{
    public DatabaseManagerConnector manager;

    private final String GET_ALL_INFO = "SELECT * FROM developer";
    private final String FIND_BY_NAME = "SELECT * FROM developer WHERE lastName LIKE ? and firstName LIKE ? ";

    public DeveloperStorage (DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }




    @Override
    public DeveloperDao save(DeveloperDao entity) {
        return null;
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
    public List<DeveloperDao> findAll() {
        List<DeveloperDao> developerDaoList = new ArrayList<>();
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
                    developerDao.setCompany_id(rs.getInt("company_id"));
                    developerDao.setSalary(rs.getInt("salary"));
                    developerDaoList.add(developerDao);
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
            developerDao.setCompany_id(resultSet.getLong("developer_id"));
            developerDao.setLastName(resultSet.getString("lastName"));
            developerDao.setFirstName(resultSet.getString("firstName"));
            developerDao.setAge(resultSet.getInt("age"));
            developerDao.setSalary(resultSet.getInt("salary"));
            developerDao.setCompany_id(resultSet.getLong("company_id"));
        }
        return developerDao;
    }

}
