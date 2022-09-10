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

    private final PreparedStatement getAllInfoSt;


    public ProjectStorage (DatabaseManagerConnector manager) throws SQLException {
        this.manager = manager;
        Connection connection = null;
        try {
            connection = manager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        {
            getAllInfoSt = connection.prepareStatement("SELECT * FROM project");

        }


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
        try {
            Connection connection = manager.getConnection();
            try (ResultSet rs = getAllInfoSt.executeQuery()) {
                while (rs.next()) {
                    ProjectDao projectDao = new ProjectDao();
                    projectDao.setProject_id(rs.getLong("project_id"));
                    projectDao.setProject_name(rs.getString("project_name"));
                    projectDao.setCost(rs.getInt("cost"));
                    projectDao.setStart_date(LocalDate.parse(rs.getString("start_date"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    /*
                    String str = "2016-03-04 11:30";
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
                    */
                    projectDaoList.add(projectDao);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
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
