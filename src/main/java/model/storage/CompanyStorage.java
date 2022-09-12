package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyStorage implements Storage<CompanyDao> {
    public DatabaseManagerConnector manager;

    private final String GET_ALL_INFO = "SELECT * FROM company";
    private final String FIND_BY_NAME = "SELECT * FROM company WHERE   company_name  LIKE  ?";
    private final String INSERT = "INSERT INTO company(company_name, rating) VALUES (?, ?)";


    public CompanyStorage (DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }



    @Override
    public CompanyDao save(CompanyDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getCompany_name());
            statement.setString(2, entity.getRating().toString());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setCompany_id(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Company saving was interrupted, ID has not been obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("The company was not created");
        }
        return entity;
    }

    @Override
    public Optional<CompanyDao> findById(long id) {
        return null;
    }

    @Override
    public Optional<CompanyDao> findByName(String name) {
        try(Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
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
        try (Connection connection = manager.getConnection();
            ResultSet rs = connection.prepareStatement(GET_ALL_INFO).executeQuery()) {
                while (rs.next()) {
                    CompanyDao companyDao = new CompanyDao();
                    companyDao.setCompany_id(rs.getLong("company_id"));
                    companyDao.setCompany_name(rs.getString("company_name"));
                    companyDao.setRating(CompanyDao.Rating.valueOf(rs.getString("rating")));
                    companyDaoList.add(companyDao);
                }
            }
        catch (SQLException exception) {
            exception.printStackTrace();
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
