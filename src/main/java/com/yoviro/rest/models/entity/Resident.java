package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "resident")
@NamedQueries({
        @NamedQuery(name = "Resident.findByOfficialID",
                query = "select r from Resident r \n" +
                        "inner join Contact c on r.contact.id = c.id \n" +
                        "inner join OfficialId o on o.contact.id = c.id \n" +
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
    private Contact contact; //Paciente Adulto Mayor

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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