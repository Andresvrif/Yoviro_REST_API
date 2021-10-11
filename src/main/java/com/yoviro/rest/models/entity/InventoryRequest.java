package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "InventoryRequest.summaryListByNurseUserNameWithCreateAtDesc",
                query = "select ir.inventoryRequestNumber as inventoryRequestNumber, " +
                        "rp.name as residentFirstName, " +
                        "rp.secondName as residentSecondName, " +
                        "rp.lastName as residentLastName, " +
                        "rp.secondName as residentSecondLastName, " +
                        "ir.status as status, " +
                        "skp.name as storeKeeperFirstName, " +
                        "skp.secondName as storeKeeperSecondName, " +
                        "skp.lastName as storeKeeperLastName, " +
                        "skp.secondLastName as storeKeeperSecondLastName, " +
                        "p.proposalNumber as proposalNumber, " +
                        "ir.createAt as createAt " +
                        "from InventoryRequest ir " +
                        "left join ir.proposals irp " +
                        "left join Proposal p on irp.id = p.id " +
                        "left join StoreKeeper sk on p.author = sk " +
                        "left join Person skp on sk.person = skp " +
                        "inner join Resident r on r = ir.resident " +
                        "inner join Person rp on rp = r.person " +
                        "inner join Nurse n on n.user = ir.author " +
                        "inner join User nu on nu = n.user " +
                        "where nu.username = ?1 " +
                        "order by ir.createAt desc"),
        @NamedQuery(name = "InventoryRequest.summaryListByNurseUserNameWithCreateAtAsc",
                query = "select ir.inventoryRequestNumber as inventoryRequestNumber, " +
                        "rp.name as residentFirstName, " +
                        "rp.secondName as residentSecondName, " +
                        "rp.lastName as residentLastName, " +
                        "rp.secondName as residentSecondLastName, " +
                        "ir.status as status, " +
                        "skp.name as storeKeeperFirstName, " +
                        "skp.secondName as storeKeeperSecondName, " +
                        "skp.lastName as storeKeeperLastName, " +
                        "skp.secondLastName as storeKeeperSecondLastName, " +
                        "p.proposalNumber as proposalNumber, " +
                        "ir.createAt as createAt " +
                        "from InventoryRequest ir " +
                        "left join ir.proposals irp " +
                        "left join Proposal p on irp.id = p.id " +
                        "left join StoreKeeper sk on p.author = sk " +
                        "left join Person skp on sk.person = skp " +
                        "inner join Resident r on r = ir.resident " +
                        "inner join Person rp on rp = r.person " +
                        "inner join Nurse n on n.user = ir.author " +
                        "inner join User nu on nu = n.user " +
                        "where nu.username = ?1 " +
                        "order by ir.createAt asc")
})
public class InventoryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Generated(GenerationTime.INSERT)
    private String inventoryRequestNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker author;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InventoryRequestStatusEnum status;

    @NotNull
    @OneToMany(mappedBy = "inventoryRequest", fetch = FetchType.LAZY)
    private List<InventoryRequestDetail> details = new ArrayList<>();

    @NotNull
    @ManyToMany(mappedBy = "inventoryRequests")
    private List<Proposal> proposals = new ArrayList<>();

    @NotNull
    private LocalDateTime createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryRequestNumber() {
        return inventoryRequestNumber;
    }

    public void setInventoryRequestNumber(String inventoryRequestNumber) {
        this.inventoryRequestNumber = inventoryRequestNumber;
    }

    public Worker getAuthor() {
        return author;
    }

    public void setAuthor(Worker worker) {
        this.author = worker;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public InventoryRequestStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InventoryRequestStatusEnum status) {
        this.status = status;
    }

    public List<InventoryRequestDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InventoryRequestDetail> details) {
        this.details = details;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @PrePersist
    public void PrePersist() {
        this.createAt = LocalDateTime.now();
        this.status = InventoryRequestStatusEnum.PENDING;
    }

    private static final long serialVersionUID = 1L;
}
