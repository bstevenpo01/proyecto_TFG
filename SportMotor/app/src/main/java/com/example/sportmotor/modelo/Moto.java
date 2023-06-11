package com.example.sportmotor.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Moto implements Serializable {
    private String nbastidorm, marcam, modelom;
    private double preciom;
    private String foto;


    public Moto(String nbastidorm, String marcam, String modelom, double preciom, String foto) {
        this.nbastidorm = nbastidorm;
        this.marcam = marcam;
        this.modelom = modelom;
        this.preciom = preciom;
        this.foto = foto;
    }

    public Moto( String marcam, String modelom, double preciom) {
        this.nbastidorm = "";
        this.marcam = marcam;
        this.modelom = modelom;
        this.preciom = preciom;
        this.foto = null;
    }

    public Moto(String nbastidorm, String marcam, String modelom, double preciom) {
        this.nbastidorm = nbastidorm;
        this.marcam = marcam;
        this.modelom = modelom;
        this.preciom = preciom;
        this.foto = null;
    }

    public Moto( String marcam, String modelom, double preciom, String foto) {
        this.nbastidorm = "";
        this.marcam = marcam;
        this.modelom = modelom;
        this.preciom = preciom;
        this.foto = foto;
    }

    public Moto() {
        this.nbastidorm = "";
        this.marcam = "";
        this.modelom = "";
        this.preciom = 0.0;
        this.foto = null;
    }

    public String getNbastidorm() {
        return nbastidorm;
    }

    public void setNbastidorm(String nbastidorm) {
        this.nbastidorm = nbastidorm;
    }

    public String getMarcam() {
        return marcam;
    }

    public void setMarcam(String marcam) {
        this.marcam = marcam;
    }

    public String getModelom() {
        return modelom;
    }

    public void setModelom(String modelom) {
        this.modelom = modelom;
    }

    public double getPreciom() {
        return preciom;
    }

    public void setPreciom(double preciom) {
        this.preciom = preciom;
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
        Moto moto = (Moto) o;
        return Double.compare(moto.preciom, preciom) == 0 && Objects.equals(nbastidorm, moto.nbastidorm) && Objects.equals(marcam, moto.marcam) && Objects.equals(modelom, moto.modelom) && Objects.equals(foto, moto.foto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbastidorm, marcam, modelom, preciom, foto);
    }

    @Override
    public String toString() {
        return "Moto{" +
                "nbastidorm='" + nbastidorm + '\'' +
                ", marcam='" + marcam + '\'' +
                ", modelom='" + modelom + '\'' +
                ", preciom=" + preciom +
                ", foto='" + foto + '\'' +
                '}';
    }
}
