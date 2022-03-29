package com.backbase.recruitment.service.mapper;

import java.util.Collection;

/***
 *
 * @param <D> - DTO type parameter
 * @param <E> - Entity type parameter
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    Collection<D> toDto(Collection<E> entityList);

    Collection<E> toEntity(Collection<D> dtoList);
}
