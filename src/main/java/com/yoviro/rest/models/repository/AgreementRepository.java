package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.models.repository.projections.AgreementResidentProjection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends PagingAndSortingRepository<Agreement, Long>,
        JpaSpecificationExecutor<Agreement> {
    Agreement findAgreementByAgreementNumber(String agreementNumber);
}
