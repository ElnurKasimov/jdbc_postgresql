package storage;

import lombok.Data;
import model.dao.DeveloperDao;

import java.util.List;

@Data
public class DeveloperStorage implements Storage<DeveloperDao>{
    @Override
    public DeveloperDao save(DeveloperDao entity) {
        return null;
    }

    @Override
    public DeveloperDao findById(long id) {
        return null;
    }

    @Override
    public DeveloperDao findByName(String name) {
        return null;
    }

    @Override
    public List<DeveloperDao> findAll() {
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
    public DeveloperDao update(DeveloperDao entity) {
        return null;
    }

    @Override
    public void delete(DeveloperDao entity) {

    }
}
