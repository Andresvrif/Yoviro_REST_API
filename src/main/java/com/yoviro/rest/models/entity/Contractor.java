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
@Table(name = "contractor")
@NamedQueries({
        @NamedQuery(name = "Contractor.findByOfficialID",
                query = "select cr from Contractor cr \n" +
                        "inner join Contact c on cr.contact.id = c.id \n" +
                        "inner join OfficialId o on o.contact.id = c.id \n" +
                        "where o.officialIdType = ?1 and o.officialIdNumber = ?2")
})
public class Contractor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractorNumber;

    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_id", nullable = false)
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
