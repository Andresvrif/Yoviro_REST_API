package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contractor", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contact_id"})
})
public class Contractor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractorNumber;

    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToMany(mappedBy = "contractor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Agreement> agreements;

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

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

    public String getContractorNumber() {
        return contractorNumber;
    }

    public void setContractorNumber(String contractorNumber) {
        this.contractorNumber = contractorNumber;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<Agreement> agreements) {
        this.agreements = agreements;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
