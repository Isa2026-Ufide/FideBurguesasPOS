package fideburguesas.model;

import fideburguesas.enums.Rol;

public class Cajero extends Usuario {

    private Sucursal sucursalAsignada;

    public Cajero(String id,String nombre,String username,String password,Sucursal s){
        super(id,nombre,username,password,Rol.CAJERO);
        this.sucursalAsignada=s;
    }

    public void asignarSucursal(Sucursal s){
        sucursalAsignada=s;
    }

    public Sucursal getSucursal(){
        return sucursalAsignada;
    }
}
