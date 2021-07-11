package com.yoviro.rest.models.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contratantes")
public class Contratante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroDeContratante;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacto_id")
    @JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
    private Contacto contacto;

    @JsonManagedReference
    @OneToMany(mappedBy = "contratante",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Contrato> contratos;

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public String getNumeroDeContratante() {
        return numeroDeContratante;
    }

    public void setNumeroDeContratante(String numeroDeContratante) {
        this.numeroDeContratante = numeroDeContratante;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
