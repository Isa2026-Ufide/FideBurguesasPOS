package fideburguesas.model;

import fideburguesas.enums.Rol;
import fideburguesas.util.HashUtil;
import java.io.Serializable;

public class Usuario implements Serializable {

    protected String id;
    protected String nombre;
    protected String username;
    protected String passwordHash;
    protected Rol rol;
    protected boolean activo;

    public Usuario(String id, String nombre, String username, String passwordPlano, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.passwordHash = HashUtil.sha256(passwordPlano);
        this.rol = rol;
        this.activo = true;
    }

    public boolean verificarPassword(String password) {
        return passwordHash.equals(HashUtil.sha256(password));
    }

    public void cambiarPassword(String nueva) {
        passwordHash = HashUtil.sha256(nueva);
    }

    public void activar() {
        activo = true;
    }

    public void desactivar() {
        activo = false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }
}