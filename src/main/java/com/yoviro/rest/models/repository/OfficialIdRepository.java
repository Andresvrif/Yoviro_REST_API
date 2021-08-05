package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.OfficialId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficialIdRepository extends CrudRepository<OfficialId, Long> {
}
