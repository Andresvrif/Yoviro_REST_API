package com.yoviro.rest.models.repository;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.models.entity.Resident;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends CrudRepository<Resident, Long>,
        JpaSpecificationExecutor<Resident> {
    Resident findByOfficialID(OfficialIdTypeEnum officialIDTypeEnum, String officialIDNumber);
}