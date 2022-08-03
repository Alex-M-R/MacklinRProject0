package dev.macklinr.daos;

import java.util.List;

public interface CrudDAO<T>
{
    T createEntity(T entity);

    T getEntityById(int id);

    List<T> getALlEntities();

    T updateEntity(T Entity);
}
