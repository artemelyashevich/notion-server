package com.aelyashevich.notion.service.contract;

import java.util.List;

public interface CrudService<T> {

    List<T> findAll(final String id);

    T findById(final String id);

    T create(final T entity);

    T update(final String id, final T oldEntity);

    void delete(final String id);
}
