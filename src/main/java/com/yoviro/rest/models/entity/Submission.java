package com.yoviro.rest.models.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SUBMISSION")
public class Submission extends Job {
    @PrePersist
    public void PrePersist() {
        this.effectiveDate = startDate;
    }
}