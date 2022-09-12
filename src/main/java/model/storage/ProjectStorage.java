package model.storage;

import model.config.DatabaseManagerConnector;
import model.dao.CompanyDao;
import model.dao.ProjectDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectStorage implements Storage<ProjectDao> {

    public DatabaseManagerConnector manager;

    private final String GET_ALL_INFO = "SELECT * FROM project";


    public ProjectStorage (DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
    }



    @Override
    public ProjectDao save(ProjectDao entity) {
        return null;
    }

    @Override
    public Optional<ProjectDao> findById(long id) {
        return null;
    }

    @Override
    public Optional<ProjectDao> findByName(String name) {
        return null;
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
                    projectDao.setStart_date(LocalDate.parse(rs.getString("start_date"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
}
