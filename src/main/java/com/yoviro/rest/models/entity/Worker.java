package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.WorkShiftEnum;

import javax.persistence.*;

@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkShiftEnum workShiftEnum;

    @OneToOne
    @JoinColumn(name = "contact_id", unique = true)
    private Contact contact;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkShiftEnum getWorkShiftEnum() {
        return workShiftEnum;
    }

    public void setWorkShiftEnum(WorkShiftEnum workShiftEnum) {
        this.workShiftEnum = workShiftEnum;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static final long serialVersionUID = 1L;

}
