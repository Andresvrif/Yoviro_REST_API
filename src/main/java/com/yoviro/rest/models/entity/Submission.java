package com.yoviro.rest.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "submission")
@DiscriminatorValue("submission")
public class Submission extends Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    private static final long serialVersionUID = 1L;
}
