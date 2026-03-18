package fideburguesas.ui;

import fideburguesas.enums.EstadoOrden;
import fideburguesas.model.Cajero;
import fideburguesas.model.Combo;
import fideburguesas.model.LineaOrden;
import fideburguesas.model.Orden;
import fideburguesas.model.Producto;
import fideburguesas.repository.DataStore;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NuevaOrdenFrame extends JFrame {

    private Cajero cajero;
    private Orden ordenActual;

    private DefaultTableModel modeloCatalogo;
    private DefaultTableModel modeloCarrito;

    private JTable tablaCatalogo;
    private JTable tablaCarrito;

    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private JLabel lblMensaje;

    public NuevaOrdenFrame(Cajero cajero) {
        this.cajero = cajero;

        String idOrden = String.valueOf(DataStore.ordenes.size() + 1);
        ordenActual = new Orden(idOrden, cajero);

        setTitle("Nueva Orden");
        setSize(920, 560);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel encabezado = new JLabel("Nueva Orden");
        encabezado.setBounds(20, 10, 120, 25);
        add(encabezado);

        JLabel info = new JLabel("Cajero: " + cajero.getNombre() +
                "   Sucursal: " + cajero.getSucursal().getNombre() +
                "   Fecha/Hora: " + ordenActual.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        info.setBounds(20, 40, 600, 25);
        add(info);

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(20, 75, 60, 25);
        add(lblBuscar);

        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(80, 75, 180, 25);
        add(txtBuscar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(270, 75, 90, 25);
        add(btnBuscar);

        JLabel lblCat = new JLabel("CATÁLOGO (Productos / Combos)");
        lblCat.setBounds(20, 110, 250, 25);
        add(lblCat);

        JLabel lblCarr = new JLabel("CARRITO (Líneas de orden)");
        lblCarr.setBounds(470, 110, 220, 25);
        add(lblCarr);

        modeloCatalogo = new DefaultTableModel(new String[]{"Tipo", "Nombre", "Precio", "Ref"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Producto p : DataStore.productos) {
            modeloCatalogo.addRow(new Object[]{"Producto", p.getNombre(), p.getPrecio(), p});
        }
        for (Combo c : DataStore.combos) {
            modeloCatalogo.addRow(new Object[]{"Combo", c.getNombre(), c.calcularPrecio(), c});
        }

        tablaCatalogo = new JTable(modeloCatalogo);
        tablaCatalogo.removeColumn(tablaCatalogo.getColumnModel().getColumn(3));
        JScrollPane sp1 = new JScrollPane(tablaCatalogo);
        sp1.setBounds(20, 140, 400, 220);
        add(sp1);

        modeloCarrito = new DefaultTableModel(new String[]{"Ítem", "Cant", "Precio", "Subt"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaCarrito = new JTable(modeloCarrito);
        JScrollPane sp2 = new JScrollPane(tablaCarrito);
        sp2.setBounds(470, 140, 400, 220);
        add(sp2);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(150, 370, 120, 30);
        add(btnAgregar);

        JButton btnEliminar = new JButton("ELIMINAR LÍNEA");
        btnEliminar.setBounds(470, 370, 150, 30);
        add(btnEliminar);

        JButton btnVaciar = new JButton("VACIAR CARRITO");
        btnVaciar.setBounds(640, 370, 150, 30);
        add(btnVaciar);

        lblSubtotal = new JLabel("Subtotal: ₡ 0");
        lblSubtotal.setBounds(470, 410, 160, 25);
        add(lblSubtotal);

        lblIva = new JLabel("IVA (13%): ₡ 0");
        lblIva.setBounds(470, 435, 160, 25);
        add(lblIva);

        lblTotal = new JLabel("Total: ₡ 0");
        lblTotal.setBounds(470, 460, 160, 25);
        add(lblTotal);

        JButton btnEnviar = new JButton("ENVIAR A COCINA");
        btnEnviar.setBounds(20, 450, 170, 30);
        add(btnEnviar);

        JButton btnBorrador = new JButton("GUARDAR BORRADOR");
        btnBorrador.setBounds(210, 450, 180, 30);
        add(btnBorrador);

        JButton btnCancelar = new JButton("CANCELAR ORDEN");
        btnCancelar.setBounds(20, 485, 170, 30);
        add(btnCancelar);

        lblMensaje = new JLabel("Mensaje:");
        lblMensaje.setBounds(210, 490, 500, 20);
        add(lblMensaje);

        btnAgregar.addActionListener(e -> agregarItemSeleccionado());
        btnEliminar.addActionListener(e -> eliminarLinea());
        btnVaciar.addActionListener(e -> vaciarCarrito());
        btnEnviar.addActionListener(e -> enviarACocina());
        btnBorrador.addActionListener(e -> guardarBorrador());
        btnCancelar.addActionListener(e -> cancelarOrden());
        btnBuscar.addActionListener(e -> buscarCatalogo(txtBuscar.getText().trim()));
    }

    private void buscarCatalogo(String filtro) {
        modeloCatalogo.setRowCount(0);

        for (Producto p : DataStore.productos) {
            if (filtro.isEmpty() || p.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                modeloCatalogo.addRow(new Object[]{"Producto", p.getNombre(), p.getPrecio(), p});
            }
        }

        for (Combo c : DataStore.combos) {
            if (filtro.isEmpty() || c.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                modeloCatalogo.addRow(new Object[]{"Combo", c.getNombre(), c.calcularPrecio(), c});
            }
        }
    }

    private void agregarItemSeleccionado() {
        int fila = tablaCatalogo.getSelectedRow();

        if (fila == -1) {
            lblMensaje.setText("Mensaje: seleccione un producto o combo.");
            return;
        }

        Object ref = modeloCatalogo.getValueAt(fila, 3);
        String cantidadStr = JOptionPane.showInputDialog(this, "Cantidad:", "1");

        if (cantidadStr == null) return;

        try {
            int cantidad = Integer.parseInt(cantidadStr);

            if (cantidad <= 0) {
                lblMensaje.setText("Mensaje: cantidad inválida.");
                return;
            }

            if (ref instanceof Producto) {
                ordenActual.agregarProducto((Producto) ref, cantidad);
            } else if (ref instanceof Combo) {
                ordenActual.agregarCombo((Combo) ref, cantidad);
            }

            recargarCarrito();
            lblMensaje.setText("Mensaje: ítem agregado correctamente.");
        } catch (NumberFormatException ex) {
            lblMensaje.setText("Mensaje: cantidad debe ser numérica.");
        }
    }

    private void recargarCarrito() {
        modeloCarrito.setRowCount(0);

        for (LineaOrden l : ordenActual.getLineas()) {
            modeloCarrito.addRow(new Object[]{
                l.getDescripcion(),
                l.getCantidad(),
                l.getPrecioUnitario(),
                l.calcularSubtotal()
            });
        }

        lblSubtotal.setText("Subtotal: ₡ " + ordenActual.calcularSubtotal());
        lblIva.setText("IVA (13%): ₡ " + ordenActual.calcularImpuesto(0.13));
        lblTotal.setText("Total: ₡ " + ordenActual.calcularTotal());
    }

    private void eliminarLinea() {
        int fila = tablaCarrito.getSelectedRow();

        if (fila == -1) {
            lblMensaje.setText("Mensaje: seleccione una línea del carrito.");
            return;
        }

        ordenActual.getLineas().remove(fila);
        recargarCarrito();
        lblMensaje.setText("Mensaje: línea eliminada.");
    }

    private void vaciarCarrito() {
        ordenActual.getLineas().clear();
        recargarCarrito();
        lblMensaje.setText("Mensaje: carrito vaciado.");
    }

    private void enviarACocina() {
        if (ordenActual.getLineas().isEmpty()) {
            lblMensaje.setText("Mensaje: no puede enviar a cocina si el carrito está vacío.");
            return;
        }

        ordenActual.cambiarEstado(EstadoOrden.PENDIENTE);
        DataStore.ordenes.add(ordenActual);
        lblMensaje.setText("Mensaje: orden enviada a cocina.");
    }

    private void guardarBorrador() {
        lblMensaje.setText("Mensaje: borrador guardado (opcional).");
    }

    private void cancelarOrden() {
        int r = JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar la orden?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (r == JOptionPane.YES_OPTION) {
            ordenActual.getLineas().clear();
            recargarCarrito();
            lblMensaje.setText("Mensaje: orden cancelada.");
        }
    }
}
