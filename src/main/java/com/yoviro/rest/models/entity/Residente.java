package com.yoviro.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "residentes")
public class Residente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Datos de información del residente
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacto_id")
    private Contacto PAM; //Paciente Adulto Mayor

    //Datos de información del residente
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contratante_id")
    private Contacto Contratante; //Paciente Adulto Mayor

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @NotEmpty
    private Boolean habilitado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contacto getPAM() {
        return PAM;
    }

    public void setPAM(Contacto PAM) {
        this.PAM = PAM;
    }

    public Contacto getContratante() {
        return Contratante;
    }

    public void setContratante(Contacto contratante) {
        Contratante = contratante;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    private static final long serialVersionUID = 1L;
}