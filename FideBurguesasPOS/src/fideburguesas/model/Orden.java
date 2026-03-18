package fideburguesas.model;

import fideburguesas.enums.EstadoOrden;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Orden {

    private String id;
    private LocalDateTime fechaHora;
    private Cajero cajero;
    private Sucursal sucursal;
    private List<LineaOrden> lineas=new ArrayList<>();
    private EstadoOrden estado;

    public Orden(String id,Cajero c){

        this.id=id;
        cajero=c;
        sucursal=c.getSucursal();
        fechaHora=LocalDateTime.now();
        estado=EstadoOrden.PENDIENTE;
    }

    public void agregarProducto(Producto p,int cantidad){
        lineas.add(new LineaOrden(p,cantidad));
    }

    public void agregarCombo(Combo c,int cantidad){
        lineas.add(new LineaOrden(c,cantidad));
    }

    public double calcularSubtotal(){

        double total=0;

        for(LineaOrden l:lineas)
            total+=l.calcularSubtotal();

        return total;
    }

    public double calcularImpuesto(double tasa){
        return calcularSubtotal()*tasa;
    }

    public double calcularTotal(){
        return calcularSubtotal()+calcularImpuesto(0.13);
    }

    public void cambiarEstado(EstadoOrden nuevo){
        estado=nuevo;
    }

    public List<LineaOrden> getLineas(){
        return lineas;
    }

    public EstadoOrden getEstado(){
        return estado;
    }

    public String getId(){
        return id;
    }

    public LocalDateTime getFechaHora(){
        return fechaHora;
    }

    public Sucursal getSucursal(){
        return sucursal;
    }
}