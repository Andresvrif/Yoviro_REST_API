package com.yoviro.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "solicitudes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contratos_id", "residente_id", "id"})
})//Asegurar que solo exista esta solicitud para el contrato con el residente
@DiscriminatorColumn(name = "tipo_solicitud", discriminatorType = DiscriminatorType.STRING)
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contratos_id")
    private Contrato contrato;

    @NotNull
    @Column(name = "numero_de_solicitud")
    private String numeroDeSolicitud;

    /***
     * Author : Andrés V.
     * Desc : Fecha de inicio del contrato
     */
    @NotNull
    @Column(name = "fecha_inicio")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    /***
     * Author : Andrés V.
     * Desc : Fecha de fin del contrato
     */
    @Column(name = "fecha_fin")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    /***
     * Author : Andrés V.
     * Desc : Fecha efectiva de la aplicación de la solicitud
     */
    @NotNull
    @Column(name = "fecha_efectiva")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date fechaEfectiva;

    /***
     * Author : Andrés V.
     * Desc : Datos del PAM alojado en la residencia,
     * Estos pueden cambiar a través del tiempo
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residente_id")
    private Residente residente; //Contacto de referencia del PAM

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

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getNumeroDeSolicitud() {
        return numeroDeSolicitud;
    }

    public void setNumeroDeSolicitud(String numeroDeSolicitud) {
        this.numeroDeSolicitud = numeroDeSolicitud;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaEfectiva() {
        return fechaEfectiva;
    }

    public void setFechaEfectiva(Date fechaEfectiva) {
        this.fechaEfectiva = fechaEfectiva;
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
