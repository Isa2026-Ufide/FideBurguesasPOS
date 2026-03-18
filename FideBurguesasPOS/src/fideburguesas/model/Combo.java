package fideburguesas.model;

import java.util.ArrayList;
import java.util.List;

public class Combo {

    private String id;
    private String nombre;
    private List<Producto> productos = new ArrayList<>();
    private double descuento;
    private boolean activo=true;

    public Combo(String id,String nombre,double descuento){
        this.id=id;
        this.nombre=nombre;
        this.descuento=descuento;
    }

    public void agregarProducto(Producto p){
        productos.add(p);
    }

    public void eliminarProducto(Producto p){
        productos.remove(p);
    }

    public double calcularPrecio(){

        double total=0;

        for(Producto p:productos)
            total+=p.getPrecio();

        total-=descuento;

        if(total<0)
            throw new IllegalArgumentException("Descuento inválido");

        return total;
    }

    public String getNombre(){
        return nombre;
    }
}
