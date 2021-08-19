package com.yoviro.rest.models.repository;

import com.yoviro.rest.config.enums.OfficialIdType;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.OfficialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>,
        JpaSpecificationExecutor<Contact> {
    Contact findByOfficialID(OfficialIdType officialIDType, String officialIDNumber);
}