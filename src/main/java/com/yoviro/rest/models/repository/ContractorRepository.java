package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Contractor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ContractorRepository extends PagingAndSortingRepository<Contractor, Long> {
    Contractor findByOfficialID(String officialIDType, String officialIDNumber);
}