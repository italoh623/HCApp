package me.aflak.heroicuidador.crud;

import java.io.Serializable;

public class Idoso implements Serializable {
    private Integer idIdoso;
    private String nomeIdoso;
    private Double alturaIdoso;
    private Integer idadeIdoso;
    private Double pesoIdoso;
    private String sexoIdoso;

    public Integer getIdIdoso() {
        return idIdoso;
    }

    public void setIdIdoso(Integer idIdoso) {
        this.idIdoso = idIdoso;
    }

    public String getNomeIdoso() {
        return nomeIdoso;
    }

    public void setNomeIdoso(String nomeIdoso) {
        this.nomeIdoso = nomeIdoso;
    }

    public Double getAlturaIdoso() {
        return alturaIdoso;
    }

    public void setAlturaIdoso(Double alturaIdoso) {
        this.alturaIdoso = alturaIdoso;
    }

    public Integer getIdadeIdoso() {
        return idadeIdoso;
    }

    public void setIdadeIdoso(Integer idadeIdoso) {
        this.idadeIdoso = idadeIdoso;
    }

    public Double getPesoIdoso() {
        return pesoIdoso;
    }

    public void setPesoIdoso(Double pesoIdoso) {
        this.pesoIdoso = pesoIdoso;
    }

    public String getSexoIdoso() {
        return sexoIdoso;
    }

    public void setSexoIdoso(String sexoIdoso) {
        this.sexoIdoso = sexoIdoso;
    }
}
