package service.converter;

public interface Converter <E,T>{
    public E from (T entity);

    public T to (E entity);

}

