package fideburguesas.ui;

import fideburguesas.enums.EstadoOrden;
import fideburguesas.model.Orden;
import fideburguesas.repository.DataStore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FacturarOrdenFrame extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel lblMensaje;

    public FacturarOrdenFrame() {
        setTitle("FACTURAR ORDEN");
        setSize(620, 360);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("FACTURAR ORDEN");
        titulo.setBounds(20, 15, 200, 25);
        add(titulo);

        JLabel lblBuscar = new JLabel("Buscar por ID:");
        lblBuscar.setBounds(20, 50, 100, 25);
        add(lblBuscar);

        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(120, 50, 100, 25);
        add(txtBuscar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(230, 50, 90, 25);
        add(btnBuscar);

        modelo = new DefaultTableModel(new String[]{"ID", "Hora", "Total estimado"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 90, 560, 160);
        add(sp);

        JButton btnSeleccionar = new JButton("SELECCIONAR");
        btnSeleccionar.setBounds(20, 265, 130, 30);
        add(btnSeleccionar);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(170, 265, 100, 30);
        add(btnVolver);

        lblMensaje = new JLabel("Mensaje:");
        lblMensaje.setBounds(20, 300, 400, 20);
        add(lblMensaje);

        cargarOrdenes("");

        btnBuscar.addActionListener(e -> cargarOrdenes(txtBuscar.getText().trim()));

        btnSeleccionar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setText("Mensaje: seleccione una orden.");
                return;
            }

            String id = modelo.getValueAt(fila, 0).toString();

            for (Orden o : DataStore.ordenes) {
                if (o.getId().equals(id)) {
                    new FacturacionFrame(o).setVisible(true);
                    return;
                }
            }
        });

        btnVolver.addActionListener(e -> dispose());
    }

    private void cargarOrdenes(String filtroId) {
        modelo.setRowCount(0);

        for (Orden o : DataStore.ordenes) {
            boolean permitida = o.getEstado() == EstadoOrden.LISTA || o.getEstado() == EstadoOrden.ENTREGADA;
            boolean coincide = filtroId.isEmpty() || o.getId().equals(filtroId);

            if (permitida && coincide) {
                modelo.addRow(new Object[]{
                    o.getId(),
                    o.getFechaHora().toLocalTime().withNano(0),
                    o.calcularTotal()
                });
            }
        }
    }
}
