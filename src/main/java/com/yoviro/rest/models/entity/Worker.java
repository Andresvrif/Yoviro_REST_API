package com.yoviro.rest.models.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", unique = true)
    private Person person;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_shift_id")
    private WorkShift workShift;

    @OneToMany(mappedBy = "worker",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<InventoryRequest> inventoryRequests = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }

    public List<InventoryRequest> getInventoryRequests() {
        return inventoryRequests;
    }

    public void setInventoryRequests(List<InventoryRequest> inventoryRequests) {
        this.inventoryRequests = inventoryRequests;
    }

    public void setWorkShift(WorkShift workShift) {
        this.workShift = workShift;
    }

    private static final long serialVersionUID = 1L;

}
