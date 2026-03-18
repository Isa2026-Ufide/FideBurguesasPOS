package fideburguesas.model;

public class LineaOrden {

    private Producto producto;
    private Combo combo;
    private int cantidad;
    private double precioUnitario;

    public LineaOrden(Producto p,int cantidad){
        this.producto=p;
        this.cantidad=cantidad;
        this.precioUnitario=p.getPrecio();
    }

    public LineaOrden(Combo c,int cantidad){
        this.combo=c;
        this.cantidad=cantidad;
        this.precioUnitario=c.calcularPrecio();
    }

    public double calcularSubtotal(){
        return precioUnitario*cantidad;
    }

    public String getDescripcion(){
        if(producto!=null)
            return producto.getNombre();
        else
            return combo.getNombre();
    }

    public int getCantidad(){
        return cantidad;
    }

    public double getPrecioUnitario(){
        return precioUnitario;
    }
}
