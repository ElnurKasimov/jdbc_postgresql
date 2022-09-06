package storage;

import model.dao.ProjectDao;

import java.util.List;

public class ProjectStorage implements Storage<ProjectDao> {
    @Override
    public ProjectDao save(ProjectDao entity) {
        return null;
    }

    @Override
    public ProjectDao findById(long id) {
        return null;
    }

    @Override
    public ProjectDao findByName(String name) {
        return null;
    }

    @Override
    public List<ProjectDao> findAll() {
        return null;
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
