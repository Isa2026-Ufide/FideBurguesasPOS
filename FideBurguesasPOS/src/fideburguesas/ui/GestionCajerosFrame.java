package fideburguesas.ui;

import fideburguesas.model.Cajero;
import fideburguesas.model.Sucursal;
import fideburguesas.model.Usuario;
import fideburguesas.repository.DataStore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestionCajerosFrame extends JFrame {

    private JTextField txtNombre;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JComboBox<String> cmbSucursal;
    private JRadioButton rbActivo;
    private JRadioButton rbInactivo;
    private DefaultTableModel modelo;

    public GestionCajerosFrame() {
        setTitle("Gestión de Cajeros");
        setSize(760, 420);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Gestión de Cajeros");
        titulo.setBounds(20, 10, 200, 25);
        add(titulo);

        JLabel l1 = new JLabel("Nombre:");
        l1.setBounds(20, 50, 80, 25);
        add(l1);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 50, 180, 25);
        add(txtNombre);

        JLabel l2 = new JLabel("Username:");
        l2.setBounds(20, 85, 80, 25);
        add(l2);

        txtUsername = new JTextField();
        txtUsername.setBounds(100, 85, 180, 25);
        add(txtUsername);

        JLabel l3 = new JLabel("Contraseña:");
        l3.setBounds(20, 120, 80, 25);
        add(l3);

        txtPassword = new JTextField();
        txtPassword.setBounds(100, 120, 180, 25);
        add(txtPassword);

        JLabel l4 = new JLabel("Sucursal:");
        l4.setBounds(20, 155, 80, 25);
        add(l4);

        cmbSucursal = new JComboBox<>(new String[]{"San Pedro", "Curridabat"});
        cmbSucursal.setBounds(100, 155, 180, 25);
        add(cmbSucursal);

        JLabel l5 = new JLabel("Estado:");
        l5.setBounds(20, 190, 80, 25);
        add(l5);

        rbActivo = new JRadioButton("Activo", true);
        rbInactivo = new JRadioButton("Inactivo");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbActivo);
        grupo.add(rbInactivo);

        rbActivo.setBounds(100, 190, 80, 25);
        rbInactivo.setBounds(180, 190, 90, 25);

        add(rbActivo);
        add(rbInactivo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(20, 230, 100, 30);
        add(btnGuardar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(130, 230, 100, 30);
        add(btnLimpiar);

        modelo = new DefaultTableModel(new String[]{"Username", "Nombre", "Sucursal", "Estado"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(320, 50, 400, 240);
        add(sp);

        JButton btnDesactivar = new JButton("Desactivar seleccionado");
        btnDesactivar.setBounds(320, 305, 200, 30);
        add(btnDesactivar);

        recargarTabla();

        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnDesactivar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) return;

            String username = modelo.getValueAt(fila, 0).toString();

            for (Usuario u : DataStore.usuarios) {
                if (u instanceof Cajero && u.getUsername().equals(username)) {
                    u.desactivar();
                    break;
                }
            }
            recargarTabla();
        });
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (nombre.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        for (Usuario u : DataStore.usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(this, "El username debe ser único.");
                return;
            }
        }

        Sucursal s = new Sucursal(
                String.valueOf(cmbSucursal.getSelectedIndex() + 1),
                cmbSucursal.getSelectedItem().toString(),
                "Dirección"
        );

        Cajero c = new Cajero(
                String.valueOf(DataStore.usuarios.size() + 1),
                nombre,
                username,
                password,
                s
        );

        if (rbInactivo.isSelected()) {
            c.desactivar();
        }

        DataStore.usuarios.add(c);
        recargarTabla();
        limpiar();
        JOptionPane.showMessageDialog(this, "Cajero registrado.");
    }

    private void limpiar() {
        txtNombre.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbSucursal.setSelectedIndex(0);
        rbActivo.setSelected(true);
    }

    private void recargarTabla() {
        modelo.setRowCount(0);

        for (Usuario u : DataStore.usuarios) {
            if (u instanceof Cajero) {
                Cajero c = (Cajero) u;
                modelo.addRow(new Object[]{
                    c.getUsername(),
                    c.getNombre(),
                    c.getSucursal().getNombre(),
                    c.isActivo() ? "Activo" : "Inactivo"
                });
            }
        }
    }
}
