package fideburguesas.ui;

import fideburguesas.model.Usuario;
import javax.swing.*;

public class AdminFrame extends JFrame {

    public AdminFrame(Usuario admin) {
        setTitle("Panel Administrador");
        setSize(380, 260);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Panel Administrador");
        titulo.setBounds(30, 20, 180, 25);
        add(titulo);

        JButton btnCajeros = new JButton("GESTIÓN DE CAJEROS");
        btnCajeros.setBounds(30, 60, 200, 30);
        add(btnCajeros);

        JButton btnProductos = new JButton("GESTIÓN DE PRODUCTOS");
        btnProductos.setBounds(30, 100, 200, 30);
        add(btnProductos);

        JButton btnCombos = new JButton("GESTIÓN DE COMBOS");
        btnCombos.setBounds(30, 140, 200, 30);
        add(btnCombos);

        JButton btnCerrar = new JButton("CERRAR SESIÓN");
        btnCerrar.setBounds(30, 180, 200, 30);
        add(btnCerrar);

        btnCajeros.addActionListener(e -> new GestionCajerosFrame().setVisible(true));
        btnProductos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Pendiente de ampliar"));
        btnCombos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Pendiente de ampliar"));
        btnCerrar.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
