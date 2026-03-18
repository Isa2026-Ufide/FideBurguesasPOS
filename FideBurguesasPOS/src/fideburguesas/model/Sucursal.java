package fideburguesas.model;

public class Sucursal {

    private String id;
    private String nombre;
    private String direccion;

    public Sucursal(String id,String nombre,String direccion){
        this.id=id;
        this.nombre=nombre;
        this.direccion=direccion;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDireccion(){
        return direccion;
    }
}
