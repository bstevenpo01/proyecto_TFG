package com.example.sportmotor.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable {
    private String nombre, apellidos, correo, contraseña;

    public Usuario(String nombre, String apellidos, String correo, String contraseña) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contraseña = contraseña;
    }
    public Usuario() {
        this.nombre = "";
        this.apellidos = "";
        this.correo = "";
        this.contraseña = "";
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) && Objects.equals(apellidos, usuario.apellidos) && Objects.equals(correo, usuario.correo) && Objects.equals(contraseña, usuario.contraseña);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos, correo, contraseña);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
