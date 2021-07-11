package com.yoviro.rest.models.dao;


import com.yoviro.rest.models.entity.Contratante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface IContratanteDao extends JpaRepository<Contratante, Long> {

    <T>Collection<T> findAllById(Long id, Class<T> type);
}