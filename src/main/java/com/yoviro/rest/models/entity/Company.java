package com.yoviro.rest.models.entity;

import org.hibernate.annotations.MetaValue;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("COMPANY")
public class Company extends Contact{

    private LocalDate startDate;
}