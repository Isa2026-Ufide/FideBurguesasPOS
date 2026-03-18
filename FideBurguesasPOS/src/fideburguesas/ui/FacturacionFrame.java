package fideburguesas.ui;

import fideburguesas.enums.EstadoOrden;
import fideburguesas.enums.MetodoPago;
import fideburguesas.model.Factura;
import fideburguesas.model.LineaOrden;
import fideburguesas.model.Orden;
import javax.swing.*;

public class FacturacionFrame extends JFrame {

    private Orden orden;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private JTextField txtMonto;
    private JTextField txtVuelto;
    private JRadioButton rbEfectivo;
    private JRadioButton rbTarjeta;
    private JRadioButton rbSinpe;
    private JLabel lblResultado;
    private boolean facturaGenerada = false;

    public FacturacionFrame(Orden orden) {
        this.orden = orden;

        setTitle("FACTURA - Orden #" + orden.getId());
        setSize(700, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("FACTURA - Orden #" + orden.getId());
        titulo.setBounds(20, 15, 220, 25);
        add(titulo);

        JTextArea areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        StringBuilder detalle = new StringBuilder();

        for (LineaOrden l : orden.getLineas()) {
            detalle.append(l.getCantidad())
                    .append(" x ")
                    .append(l.getDescripcion())
                    .append("     ₡")
                    .append(l.calcularSubtotal())
                    .append("\n");
        }

        areaDetalle.setText(detalle.toString());

        JScrollPane sp = new JScrollPane(areaDetalle);
        sp.setBounds(20, 50, 630, 170);
        add(sp);

        lblSubtotal = new JLabel("Subtotal: ₡ " + orden.calcularSubtotal());
        lblSubtotal.setBounds(20, 240, 180, 25);
        add(lblSubtotal);

        lblIva = new JLabel("IVA (13%): ₡ " + orden.calcularImpuesto(0.13));
        lblIva.setBounds(220, 240, 180, 25);
        add(lblIva);

        lblTotal = new JLabel("Total: ₡ " + orden.calcularTotal());
        lblTotal.setBounds(430, 240, 180, 25);
        add(lblTotal);

        JLabel lblMetodo = new JLabel("Método de pago:");
        lblMetodo.setBounds(20, 285, 120, 25);
        add(lblMetodo);

        rbEfectivo = new JRadioButton("Efectivo");
        rbTarjeta = new JRadioButton("Tarjeta");
        rbSinpe = new JRadioButton("SINPE");

        rbEfectivo.setBounds(150, 285, 90, 25);
        rbTarjeta.setBounds(250, 285, 90, 25);
        rbSinpe.setBounds(350, 285, 90, 25);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbEfectivo);
        grupo.add(rbTarjeta);
        grupo.add(rbSinpe);

        add(rbEfectivo);
        add(rbTarjeta);
        add(rbSinpe);

        JLabel lblMonto = new JLabel("Monto recibido:");
        lblMonto.setBounds(20, 320, 120, 25);
        add(lblMonto);

        txtMonto = new JTextField();
        txtMonto.setBounds(150, 320, 120, 25);
        add(txtMonto);

        JLabel lblV = new JLabel("Vuelto:");
        lblV.setBounds(20, 355, 120, 25);
        add(lblV);

        txtVuelto = new JTextField();
        txtVuelto.setBounds(150, 355, 120, 25);
        txtVuelto.setEditable(false);
        add(txtVuelto);

        JButton btnCalcular = new JButton("CALCULAR VUELTO");
        btnCalcular.setBounds(300, 320, 170, 30);
        add(btnCalcular);

        JButton btnGenerar = new JButton("GENERAR FACTURA");
        btnGenerar.setBounds(20, 400, 180, 30);
        add(btnGenerar);

        JButton btnEntregada = new JButton("MARCAR ENTREGADA");
        btnEntregada.setBounds(220, 400, 180, 30);
        add(btnEntregada);

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(420, 400, 120, 30);
        add(btnCancelar);

        lblResultado = new JLabel("Resultado:");
        lblResultado.setBounds(20, 440, 600, 20);
        add(lblResultado);

        rbTarjeta.addActionListener(e -> autocompletarNoEfectivo());
        rbSinpe.addActionListener(e -> autocompletarNoEfectivo());

        btnCalcular.addActionListener(e -> calcularVuelto());
        btnGenerar.addActionListener(e -> generarFactura());
        btnEntregada.addActionListener(e -> {
            orden.cambiarEstado(EstadoOrden.ENTREGADA);
            lblResultado.setText("Resultado: orden marcada como ENTREGADA.");
        });
        btnCancelar.addActionListener(e -> cancelar());
    }

    private void autocompletarNoEfectivo() {
        double total = orden.calcularTotal();
        txtMonto.setText(String.valueOf(total));
        txtVuelto.setText("0.0");
    }

    private MetodoPago obtenerMetodo() {
        if (rbEfectivo.isSelected()) return MetodoPago.EFECTIVO;
        if (rbTarjeta.isSelected()) return MetodoPago.TARJETA;
        if (rbSinpe.isSelected()) return MetodoPago.SINPE;
        return null;
    }

    private void calcularVuelto() {
        try {
            double total = orden.calcularTotal();
            double monto = Double.parseDouble(txtMonto.getText());
            double vuelto = monto - total;
            txtVuelto.setText(String.valueOf(vuelto));
        } catch (Exception e) {
            lblResultado.setText("Resultado: monto inválido.");
        }
    }

    private void generarFactura() {
        if (facturaGenerada) {
            lblResultado.setText("Resultado: la factura ya fue generada.");
            return;
        }

        MetodoPago metodo = obtenerMetodo();
        if (metodo == null) {
            lblResultado.setText("Resultado: seleccione un método de pago.");
            return;
        }

        try {
            double monto = Double.parseDouble(txtMonto.getText());
            Factura f = new Factura("000" + orden.getId(), orden);
            f.registrarPago(monto, metodo);
            java.io.File archivo = f.generarArchivoFactura();
            facturaGenerada = true;
            orden.cambiarEstado(EstadoOrden.FACTURADA);
            txtVuelto.setText(String.valueOf(monto - orden.calcularTotal()));
            lblResultado.setText("Resultado: archivo generado: " + archivo.getName());
        } catch (Exception e) {
            lblResultado.setText("Resultado: error al generar factura.");
        }
    }

    private void cancelar() {
        if (facturaGenerada) {
            lblResultado.setText("Resultado: no se puede cancelar, ya se generó factura.");
            return;
        }

        int r = JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (r == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}
