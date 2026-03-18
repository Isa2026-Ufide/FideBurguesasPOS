package fideburguesas.ui;

import fideburguesas.model.Cajero;
import fideburguesas.model.Usuario;
import javax.swing.*;

public class CajeroFrame extends JFrame {

    private Cajero cajero;

    public CajeroFrame(Usuario usuario) {
        this.cajero = (Cajero) usuario;

        setTitle("Panel Cajero");
        setSize(420, 280);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Panel Cajero");
        titulo.setBounds(30, 20, 150, 25);
        add(titulo);

        JLabel lblUsuario = new JLabel("Usuario: " + cajero.getNombre());
        lblUsuario.setBounds(30, 50, 180, 25);
        add(lblUsuario);

        JLabel lblSucursal = new JLabel("Sucursal: " + cajero.getSucursal().getNombre());
        lblSucursal.setBounds(220, 50, 150, 25);
        add(lblSucursal);

        JButton btnNuevaOrden = new JButton("NUEVA ORDEN");
        btnNuevaOrden.setBounds(30, 90, 160, 30);
        add(btnNuevaOrden);

        JButton btnOrdenes = new JButton("ÓRDENES ACTIVAS");
        btnOrdenes.setBounds(30, 130, 160, 30);
        add(btnOrdenes);

        JButton btnFacturar = new JButton("FACTURAR ORDEN");
        btnFacturar.setBounds(30, 170, 160, 30);
        add(btnFacturar);

        JButton btnCerrar = new JButton("CERRAR SESIÓN");
        btnCerrar.setBounds(30, 210, 160, 30);
        add(btnCerrar);

        btnNuevaOrden.addActionListener(e -> new NuevaOrdenFrame(cajero).setVisible(true));
        btnOrdenes.addActionListener(e -> new OrdenesActivasFrame().setVisible(true));
        btnFacturar.addActionListener(e -> new FacturarOrdenFrame().setVisible(true));
        btnCerrar.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}