package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {
    public Usuario findByUsername(String username);
}