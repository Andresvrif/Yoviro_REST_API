package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Company;
import com.yoviro.rest.models.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
}
