package com.yoviro.rest.models.repository;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.models.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>,
        JpaSpecificationExecutor<Contact> {
    Contact findByOfficialID(OfficialIdTypeEnum officialIDTypeEnum, String officialIDNumber);
}