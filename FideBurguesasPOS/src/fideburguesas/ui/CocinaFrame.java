package fideburguesas.ui;

import fideburguesas.enums.EstadoOrden;
import fideburguesas.model.LineaOrden;
import fideburguesas.model.Orden;
import fideburguesas.repository.DataStore;
import java.util.Comparator;
import javax.swing.*;

public class CocinaFrame extends JFrame implements Runnable {

    private JTextArea area;

    public CocinaFrame() {
        setTitle("MONITOR COCINA");
        setSize(700, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("MONITOR COCINA - Sucursal");
        titulo.setBounds(20, 10, 300, 25);
        add(titulo);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(500, 10, 100, 25);
        add(btnActualizar);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(610, 10, 70, 25);
        add(btnSalir);

        area = new JTextArea();
        area.setEditable(false);
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(20, 50, 660, 340);
        add(sp);

        JButton btnPreparacion = new JButton("Marcar EN PREPARACIÓN");
        btnPreparacion.setBounds(20, 405, 220, 30);
        add(btnPreparacion);

        JButton btnLista = new JButton("Marcar LISTA por ID");
        btnLista.setBounds(260, 405, 180, 30);
        add(btnLista);

        btnActualizar.addActionListener(e -> refrescar());
        btnSalir.addActionListener(e -> dispose());

        btnPreparacion.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID de orden:");
            if (id != null) cambiarEstado(id, EstadoOrden.EN_PREPARACION);
        });

        btnLista.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID de orden:");
            if (id != null) cambiarEstado(id, EstadoOrden.LISTA);
        });

        new Thread(this).start();
    }

    private void cambiarEstado(String id, EstadoOrden nuevo) {
        for (Orden o : DataStore.ordenes) {
            if (o.getId().equals(id)) {
                o.cambiarEstado(nuevo);
                refrescar();
                return;
            }
        }
    }

    private String detalleOrden(Orden o) {
        StringBuilder sb = new StringBuilder();
        for (LineaOrden l : o.getLineas()) {
            sb.append(l.getDescripcion()).append(" x").append(l.getCantidad()).append(", ");
        }
        return sb.length() > 1 ? sb.substring(0, sb.length() - 2) : "";
    }

    private void refrescar() {
        StringBuilder s = new StringBuilder();

        s.append("PENDIENTES\n\n");
        DataStore.ordenes.stream()
                .sorted(Comparator.comparing(Orden::getFechaHora))
                .filter(o -> o.getEstado() == EstadoOrden.PENDIENTE)
                .forEach(o -> s.append("Orden #").append(o.getId())
                        .append(" | ").append(o.getFechaHora().toLocalTime().withNano(0))
                        .append(" | Detalle: ").append(detalleOrden(o))
                        .append("\n"));

        s.append("\nEN PREPARACIÓN\n\n");
        DataStore.ordenes.stream()
                .sorted(Comparator.comparing(Orden::getFechaHora))
                .filter(o -> o.getEstado() == EstadoOrden.EN_PREPARACION)
                .forEach(o -> s.append("Orden #").append(o.getId())
                        .append(" | ").append(o.getFechaHora().toLocalTime().withNano(0))
                        .append(" | Detalle: ").append(detalleOrden(o))
                        .append("\n"));

        s.append("\nLISTAS\n\n");
        DataStore.ordenes.stream()
                .sorted(Comparator.comparing(Orden::getFechaHora))
                .filter(o -> o.getEstado() == EstadoOrden.LISTA)
                .forEach(o -> s.append("Orden #").append(o.getId())
                        .append(" | ").append(o.getFechaHora().toLocalTime().withNano(0))
                        .append(" | Detalle: ").append(detalleOrden(o))
                        .append("\n"));

        area.setText(s.toString());
    }

    @Override
    public void run() {
        while (true) {
            refrescar();
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                break;
            }
        }
    }
}
