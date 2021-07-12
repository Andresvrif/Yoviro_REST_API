package com.yoviro.rest.models.dao;

import com.yoviro.rest.models.entity.Contratante;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface IContratanteDao extends PagingAndSortingRepository<Contratante, Long> {
    <T>Collection<T> findAllById(Long id, Class<T> type);
    <T> List<T> findAllByContactoNotNull(Pageable pageable, Class<T> type);
}