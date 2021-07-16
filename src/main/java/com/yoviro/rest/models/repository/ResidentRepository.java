package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Resident;
import org.springframework.data.repository.CrudRepository;

public interface ResidentRepository extends CrudRepository<Resident, Long> {
    Resident findByOfficialID(String officialIDType, String officialIDNumber);
}