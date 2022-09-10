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

    private final PreparedStatement getAllInfoSt;


    public CustomerStorage(DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
        Connection connection = null;
        try {
            connection = manager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        {
            getAllInfoSt = connection.prepareStatement("SELECT * FROM customer");

        }


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
        try {
            Connection connection = manager.getConnection();
            try (ResultSet rs = getAllInfoSt.executeQuery()) {
                while (rs.next()) {
                    CustomerDao customerDao = new CustomerDao();
                    customerDao.setCustomer_id(rs.getLong("customer_id"));
                    customerDao.setCustomer_name(rs.getString("customer_name"));
                    customerDao.setReputation(CustomerDao.Reputation.valueOf(rs.getString("reputation")));
                    customerDaoList.add(customerDao);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
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
