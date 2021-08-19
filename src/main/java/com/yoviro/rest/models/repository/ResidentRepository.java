package com.yoviro.rest.models.repository;

import com.yoviro.rest.config.enums.OfficialIdType;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Resident;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends CrudRepository<Resident, Long>,
        JpaSpecificationExecutor<Resident> {
    Resident findByOfficialID(OfficialIdType officialIDType, String officialIDNumber);
}