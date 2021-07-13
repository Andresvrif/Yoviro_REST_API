package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Contractor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface ContractorRepository extends PagingAndSortingRepository<Contractor, Long> {
    <T>Collection<T> findAllById(Long id, Class<T> type);
    <T> List<T> findAllByContactNotNull(Pageable pageable, Class<T> type);
}