package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.DeveloperDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyStorage implements Storage<CompanyDao> {
    public DatabaseManagerConnector manager;

    private PreparedStatement getAllInfoSt;
    private PreparedStatement findByNameSt;

    public CompanyStorage (DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
        Connection connection = null;
        try {
            connection = manager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        {
            getAllInfoSt = connection.prepareStatement("SELECT * FROM company");
            findByNameSt = connection.prepareStatement("SELECT * FROM company WHERE   company_name  LIKE  ?");

        }


    }



    @Override
    public CompanyDao save(CompanyDao entity) {
        return null;
    }

    @Override
    public Optional<CompanyDao> findById(long id) {
        return null;
    }

    @Override
    public Optional<CompanyDao>  findByName(String name) {
        try (Connection connection = manager.getConnection()) {
            findByNameSt.setString(1, "%" + name + "%");
            ResultSet resultSet = findByNameSt.executeQuery();
            CompanyDao companyDao = mapCompanyDao(resultSet);
            return Optional.ofNullable(companyDao);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<CompanyDao> findAll() {
        List<CompanyDao> companyDaoList = new ArrayList<>();
        try {
            Connection connection = manager.getConnection();
            try (ResultSet rs = getAllInfoSt.executeQuery()) {
                while (rs.next()) {
                    CompanyDao companyDao = new CompanyDao();
                    companyDao.setCompany_id(rs.getLong("company_id"));
                    companyDao.setCompany_name(rs.getString("company_name"));
                    companyDao.setRating(CompanyDao.Rating.valueOf(rs.getString("rating")));
                    companyDaoList.add(companyDao);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return companyDaoList;
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
    public CompanyDao update(CompanyDao entity) {
        return null;
    }

    @Override
    public void delete(CompanyDao entity) {

    }

    private CompanyDao mapCompanyDao(ResultSet resultSet) throws SQLException {
        CompanyDao companyDao = null;
        while (resultSet.next()) {
            companyDao = new CompanyDao();
            companyDao.setCompany_id(resultSet.getLong("company_id"));
            companyDao.setCompany_name(resultSet.getString("company_name"));
            companyDao.setRating(CompanyDao.Rating.valueOf(resultSet.getString("rating")));
        }
        return companyDao;
    }
}
