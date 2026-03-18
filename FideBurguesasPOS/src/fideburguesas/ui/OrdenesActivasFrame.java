package fideburguesas.ui;

import fideburguesas.model.Orden;
import fideburguesas.repository.DataStore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrdenesActivasFrame extends JFrame {

    public OrdenesActivasFrame() {
        setTitle("Órdenes Activas");
        setSize(600, 320);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Órdenes Activas");
        titulo.setBounds(20, 15, 200, 25);
        add(titulo);

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Fecha/Hora", "Estado", "Total"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Orden o : DataStore.ordenes) {
            modelo.addRow(new Object[]{
                o.getId(),
                o.getFechaHora(),
                o.getEstado(),
                o.calcularTotal()
            });
        }

        JTable tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 50, 540, 180);
        add(sp);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(20, 245, 100, 30);
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());
    }
}