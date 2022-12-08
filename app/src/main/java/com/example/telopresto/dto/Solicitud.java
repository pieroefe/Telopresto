package com.example.telopresto.dto;

public class Solicitud {

    String id;
    String tipo;
    String marca;
    String programas;
    String motivo;
    String tiempoDeSolicitud;
    String curso;
    String otros;
    String estado;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getProgramas() {
        return programas;
    }

    public void setProgramas(String programas) {
        this.programas = programas;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTiempoDeSolicitud() {
        return tiempoDeSolicitud;
    }

    public void setTiempoDeSolicitud(String tiempoDeSolicitud) {
        this.tiempoDeSolicitud = tiempoDeSolicitud;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
