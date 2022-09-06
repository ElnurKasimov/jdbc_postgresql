package storage;

import java.util.List;

public interface Storage <T> {
    T save (T entity);
    T findById (long id);
    T findByName (String name);
    List<T> findAll();
    boolean isExist(long id);
    boolean isExist(String name);
    T update (T entity);
    void delete(T entity);

}
