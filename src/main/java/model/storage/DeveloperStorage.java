package model.storage;

import lombok.Data;
import lombok.Getter;
import model.config.DatabaseManagerConnector;
import model.dao.DeveloperDao;

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
}
