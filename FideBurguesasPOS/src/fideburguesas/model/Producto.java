package fideburguesas.model;

public class Producto {

    private String id;
    private String nombre;
    private double precio;
    private String categoria;
    private boolean activo=true;

    public Producto(String id,String nombre,double precio,String categoria){

        if(precio<=0)
            throw new IllegalArgumentException("Precio debe ser mayor a 0");

        this.id=id;
        this.nombre=nombre;
        this.precio=precio;
        this.categoria=categoria;
    }

    public void actualizarPrecio(double nuevo){

        if(nuevo<=0)
            throw new IllegalArgumentException("Precio inválido");

        precio=nuevo;
    }

    public void desactivar(){
        activo=false;
    }

    public String getNombre(){
        return nombre;
    }

    public double getPrecio(){
        return precio;
    }
}
