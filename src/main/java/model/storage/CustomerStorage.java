package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.CustomerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerStorage implements Storage<CustomerDao> {
    public DatabaseManagerConnector manager;

    private final String GET_ALL_INFO = "SELECT * FROM customer";


    public CustomerStorage(DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }


    @Override
    public CustomerDao save(CustomerDao entity) {
        return null;
    }

    @Override
    public Optional<CustomerDao> findById(long id) {
        return null;
    }

    @Override
    public Optional<CustomerDao> findByName(String name) {
        return null;
    }

    @Override
    public List<CustomerDao> findAll() {
        List<CustomerDao> customerDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
            ResultSet rs = connection.prepareStatement(GET_ALL_INFO).executeQuery()) {
                while (rs.next()) {
                    CustomerDao customerDao = new CustomerDao();
                    customerDao.setCustomer_id(rs.getLong("customer_id"));
                    customerDao.setCustomer_name(rs.getString("customer_name"));
                    customerDao.setReputation(CustomerDao.Reputation.valueOf(rs.getString("reputation")));
                    customerDaoList.add(customerDao);
                }
            }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return customerDaoList;
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
    public CustomerDao update(CustomerDao entity) {
        return null;
    }

    @Override
    public void delete(CustomerDao entity) {

    }
}
