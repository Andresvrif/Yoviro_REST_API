package com.yoviro.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contactos")
public class Contacto implements Serializable {

    public Contacto() {}

    @JsonCreator
    public Contacto(@JsonProperty("nombre") String nombre,
                    @JsonProperty("apellidoPaterno") String apellidoPaterno,
                    @JsonProperty("apellidoMaterno") String apellidoMaterno,
                    @JsonProperty("fechaNacimiento") Date fechaNacimiento,
                    @JsonProperty("email") String email,
                    @JsonProperty("foto") String foto,
                    @JsonProperty("documentosDeIdentidad") List<DocumentoDeIdentidad> documentosDeIdentidad,
                    @JsonProperty("createAt") Date createAt) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.foto = foto;
        if(documentosDeIdentidad != null){
            this.documentosDeIdentidad = documentosDeIdentidad;
            this.documentosDeIdentidad.forEach(e -> e.setContacto(this));
        }
        this.createAt = createAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String nombre;

    @NotEmpty
    @NotNull
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @NotEmpty
    @NotNull
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @NotNull
    @Column(name = "fecha_nacimiento")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Email
    private String email;

    private String foto;

    @NotEmpty
    @JsonManagedReference
    @OneToMany(mappedBy = "contacto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoDeIdentidad> documentosDeIdentidad;

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DocumentoDeIdentidad> getDocumentosDeIdentidad() {
        return documentosDeIdentidad;
    }

    public void setDocumentosDeIdentidad(List<DocumentoDeIdentidad> documentosDeIdentidad) {
        this.documentosDeIdentidad = documentosDeIdentidad;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}