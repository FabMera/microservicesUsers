package org.fmera.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursoUsuario)) {
            return false;
        }
        //Convertimos el objeto a CursoUsuario casteandolo.
        CursoUsuario a = (CursoUsuario) obj;
        return this.usuarioId != null && this.usuarioId.equals(a.getUsuarioId());
    }

}
