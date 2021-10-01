package com.yoviro.rest.models.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ResidentDispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    Contact dispatcher; //El despachador puede ser la aseguradora, un familiar, cualquier persona relacionada al residente

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id")
    Resident resident;

    @OneToMany(mappedBy = "residentDispatch", fetch = FetchType.LAZY)
    List<InputTransactionByDispatcher> inputTransactionsByDispatcher = new ArrayList<>();

    @OneToMany(mappedBy = "residentDispatch", fetch = FetchType.LAZY)
    List<OutputTransactionByDispatcher> outputTransactionsByDispatcher = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contact getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Contact dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public List<InputTransactionByDispatcher> getInputTransactionsByDispatcher() {
        return inputTransactionsByDispatcher;
    }

    public void setInputTransactionsByDispatcher(List<InputTransactionByDispatcher> inputTransactionsByDispatcher) {
        this.inputTransactionsByDispatcher = inputTransactionsByDispatcher;
    }

    public List<OutputTransactionByDispatcher> getOutputTransactionsByDispatcher() {
        return outputTransactionsByDispatcher;
    }

    public void setOutputTransactionsByDispatcher(List<OutputTransactionByDispatcher> outputTransactionsByDispatcher) {
        this.outputTransactionsByDispatcher = outputTransactionsByDispatcher;
    }

    protected static final long serialVersionUID = 1L;
}
