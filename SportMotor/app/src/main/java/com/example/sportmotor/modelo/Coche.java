package com.example.sportmotor.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Coche implements Serializable {
    private String nbastidor, marca, modelo;
    private double precio;
    private String foto;

    //-------------------------------------------------


    public Coche(String nbastidor, String marca, String modelo, double precio, String foto) {
        this.nbastidor = nbastidor;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.foto = foto;
    }
    public Coche(String marca, String modelo, double precio) {
        this.nbastidor = "";
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.foto = null;
    }
    public Coche(String nbastidor, String marca, String modelo, double precio) {
        this.nbastidor = nbastidor;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.foto = null;
    }

    public Coche( String marca, String modelo, double precio, String foto) {
        this.nbastidor = "";
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.foto = foto;
    }

    public Coche( ) {
        this.nbastidor = "";
        this.marca = "";
        this.modelo = "";
        this.precio = 0.0;
        this.foto = null;
    }


    public String getNbastidor() {
        return nbastidor;
    }

    public void setNbastidor(String nbastidor) {
        this.nbastidor = nbastidor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coche coche = (Coche) o;
        return Double.compare(coche.precio, precio) == 0 && Objects.equals(nbastidor, coche.nbastidor) && Objects.equals(marca, coche.marca) && Objects.equals(modelo, coche.modelo) && Objects.equals(foto, coche.foto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbastidor, marca, modelo, precio, foto);
    }

    @Override
    public String toString() {
        return "Coche{" +
                "nbastidor='" + nbastidor + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precio=" + precio +
                ", foto='" + foto + '\'' +
                '}';
    }
}
