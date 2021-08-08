package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Resident;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends PagingAndSortingRepository<Agreement, Long> {
    public Agreement findAgreementByAgreementNumber(String agreementNumber);
}
