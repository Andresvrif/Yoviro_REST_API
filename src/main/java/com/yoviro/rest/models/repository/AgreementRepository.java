package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Resident;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgreementRepository extends PagingAndSortingRepository<Agreement, Long> {
}
