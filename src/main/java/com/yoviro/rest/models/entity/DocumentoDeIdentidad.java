package com.yoviro.rest.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "documentos_de_identidad", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contacto_id", "numero_de_documento"})
})
public class DocumentoDeIdentidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacto_id")
    private Contacto contacto;

    @NotNull
    @Column(name = "tipo_de_documento")
    private String tipoDeDocumento;

    @NotNull
    @Column(name = "numero_de_documento")
    private String numeroDeDocumento;

    public String getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(String tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    private static final long serialVersionUID = 1L;
}
