package org.fmera.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.fmera.springcloud.msvc.cursos.models.Usuario;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nombre;

    //FetchType.LAZY: Carga perezosa, solo carga los datos cuando se necesitan.especialmente cuando es una lista
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;

    @Transient //No se mapea en la base de datos,solo es un atributo de la clase .
    private List<Usuario> usuarios;
    public Curso() {
        cursoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Curso(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }
    //Metodos para agregar y eliminar cursoUsuario de la lista
    public void addCursoUsuario(CursoUsuario cursoUsuario){
        this.cursoUsuarios.add(cursoUsuario);
    }
    public void removeCursoUsuario(CursoUsuario cursoUsuario){
        this.cursoUsuarios.remove(cursoUsuario);
    }
}
