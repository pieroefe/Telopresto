package com.example.telopresto.dto;

import com.google.android.gms.tasks.OnCompleteListener;

public class Usuario {

    private String correo;
    private String codigo;
    private String rol;
    private String key;

    public Usuario(String correo, String codigo, String rol, String key) {
        this.correo = correo;
        this.codigo = codigo;
        this.rol = rol;
        this.key = key;
    }

    public Usuario() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
