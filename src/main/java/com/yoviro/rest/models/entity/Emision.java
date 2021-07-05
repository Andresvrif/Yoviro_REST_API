package com.yoviro.rest.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "emisiones")
@DiscriminatorValue("emision")
public class Emision extends Solicitud {

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
