package fideburguesas.util;

import fideburguesas.model.Factura;
import fideburguesas.model.LineaOrden;
import java.io.File;
import java.io.FileWriter;

public class GeneradorFacturaTXT {

    public static File generar(Factura factura) {

        File archivo = new File("Factura_" + factura.getId() + ".txt");

        try {
            FileWriter fw = new FileWriter(archivo);

            fw.write("FIDEBURGUESAS - " + factura.getOrden().getSucursal().getNombre() + "\n");
            fw.write("Factura: " + factura.getId() + "\n");
            fw.write("Fecha: " + factura.getOrden().getFechaHora() + "\n\n");

            for (LineaOrden l : factura.getOrden().getLineas()) {
                fw.write(l.getCantidad() + " x " +
                        l.getDescripcion() + "   " +
                        l.getPrecioUnitario() + "   " +
                        l.calcularSubtotal() + "\n");
            }

            fw.write("\nSubtotal: " + factura.getSubtotal());
            fw.write("\nIVA 13%: " + factura.getImpuesto());
            fw.write("\nTOTAL: " + factura.getTotal());
            fw.write("\nMétodo: " + factura.getMetodoPago());
            fw.write("\nPagado: " + factura.getMontoPagado());
            fw.write("\nVuelto: " + factura.getVuelto());
            fw.write("\n\nGracias por su compra");

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return archivo;
    }
}
