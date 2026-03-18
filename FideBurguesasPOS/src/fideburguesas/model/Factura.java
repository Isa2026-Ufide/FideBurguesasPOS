package fideburguesas.model;

import fideburguesas.enums.MetodoPago;
import fideburguesas.util.GeneradorFacturaTXT;
import java.io.File;

public class Factura {

    private String id;
    private Orden orden;
    private double subtotal;
    private double impuesto;
    private double total;
    private MetodoPago metodoPago;
    private double montoPagado;
    private double vuelto;

    public Factura(String id,Orden orden){
        this.id=id;
        this.orden=orden;
        calcularMontos();
    }

    public void calcularMontos(){
        subtotal=orden.calcularSubtotal();
        impuesto=orden.calcularImpuesto(0.13);
        total=subtotal+impuesto;
    }

    public void registrarPago(double monto,MetodoPago metodo){
        metodoPago=metodo;
        montoPagado=monto;
        vuelto=monto-total;
    }

    public File generarArchivoFactura(){
        return GeneradorFacturaTXT.generar(this);
    }

    public String getId() {
        return id;
    }

    public Orden getOrden() {
        return orden;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getTotal() {
        return total;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public double getVuelto() {
        return vuelto;
    }
}