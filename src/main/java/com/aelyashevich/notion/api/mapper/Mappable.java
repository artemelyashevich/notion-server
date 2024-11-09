package com.aelyashevich.notion.api.mapper;

import java.util.List;

public interface Mappable<T, D> {

    T toEntity(final D dto);

    D toDto(final T entity);

    List<D> toDto(final List<T> entities);
}
