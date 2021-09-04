package com.yoviro.rest.models.repository;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.models.entity.Contractor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepository extends PagingAndSortingRepository<Contractor, Long> {
    Contractor findByOfficialID(OfficialIdTypeEnum officialIDTypeEnum,
                                String officialIDNumber);
}