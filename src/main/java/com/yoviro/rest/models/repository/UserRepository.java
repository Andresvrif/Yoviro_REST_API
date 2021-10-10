package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.entity.User;
import com.yoviro.rest.models.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
}