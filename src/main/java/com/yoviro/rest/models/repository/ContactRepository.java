package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.OfficialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByOfficialID(String officialIDType, String officialIDNumber);
}