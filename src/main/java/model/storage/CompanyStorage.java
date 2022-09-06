package model.storage;

import model.dao.CompanyDao;

import java.util.List;

public class CompanyStorage implements  Storage<CompanyDao> {
    @Override
    public CompanyDao save(CompanyDao entity) {
        return null;
    }

    @Override
    public CompanyDao findById(long id) {
        return null;
    }

    @Override
    public CompanyDao findByName(String name) {
        return null;
    }

    @Override
    public List<CompanyDao> findAll() {
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
    public CompanyDao update(CompanyDao entity) {
        return null;
    }

    @Override
    public void delete(CompanyDao entity) {

    }
}
