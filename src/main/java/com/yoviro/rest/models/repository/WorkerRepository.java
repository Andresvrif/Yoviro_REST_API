package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {
    public Worker findWorkerByUserUsername(String username);
}
