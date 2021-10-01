package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "resident", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contact_id"})
})
@NamedQueries({
        @NamedQuery(name = "Resident.findByOfficialID",
                query = "select r from Resident r \n" +
                        "inner join Person p on r.person = p \n" +
                        "inner join OfficialId o on o.contact = p \n" +
                        "where o.officialIdType = ?1 and o.officialIdNumber = ?2")
})
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Datos de contacto del residente
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Person person; //Paciente Adulto Mayor

    @OneToMany(mappedBy = "resident",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<VitalSign> vitalSigns = new ArrayList<>();

    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY)
    private List<InventoryRequest> inventoryRequests = new ArrayList<>();

    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY)
    private List<ResidentDispatch> residentDispatches = new ArrayList<>();

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @NotNull
    private Boolean enable;

    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
    }

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

    public List<VitalSign> getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(List<VitalSign> vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    public List<VitalSign> getVitalSignsChecks() {
        return vitalSigns;
    }

    public void setVitalSignsChecks(List<VitalSign> vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    public List<InventoryRequest> getInventoryRequests() {
        return inventoryRequests;
    }

    public void setInventoryRequests(List<InventoryRequest> inventoryRequests) {
        this.inventoryRequests = inventoryRequests;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    private static final long serialVersionUID = 1L;
}