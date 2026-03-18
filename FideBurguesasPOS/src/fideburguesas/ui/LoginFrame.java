package fideburguesas.ui;

import fideburguesas.enums.Rol;
import fideburguesas.model.Usuario;
import fideburguesas.service.AuthService;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JCheckBox chkRecordar;
    private JLabel lblMensaje;

    public LoginFrame() {
        setTitle("FIDEBURGUESAS POS");
        setSize(420, 260);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("FIDEBURGUESAS POS");
        titulo.setBounds(130, 10, 200, 25);
        add(titulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(40, 50, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 50, 180, 25);
        add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(40, 85, 90, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 85, 180, 25);
        add(txtPassword);

        chkRecordar = new JCheckBox("Recordarme en este equipo");
        chkRecordar.setBounds(40, 120, 220, 25);
        add(chkRecordar);

        JButton btnLogin = new JButton("INICIAR SESIÓN");
        btnLogin.setBounds(40, 155, 150, 30);
        add(btnLogin);

        JButton btnSalir = new JButton("SALIR");
        btnSalir.setBounds(220, 155, 100, 30);
        add(btnSalir);

        lblMensaje = new JLabel("Mensaje:");
        lblMensaje.setBounds(40, 195, 330, 20);
        add(lblMensaje);

        btnLogin.addActionListener(e -> iniciarSesion());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void iniciarSesion() {
        String username = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Mensaje: usuario y contraseña obligatorios.");
            return;
        }

        AuthService auth = new AuthService();

        try {
            Usuario u = auth.login(username, password);
            lblMensaje.setText("Mensaje: acceso correcto.");

            if (u.getRol() == Rol.CAJERO) {
                new CajeroFrame(u).setVisible(true);
            } else if (u.getRol() == Rol.ADMIN) {
                new AdminFrame(u).setVisible(true);
            } else if (u.getRol() == Rol.COCINA) {
                new CocinaFrame().setVisible(true);
            }

            dispose();
        } catch (Exception ex) {
            lblMensaje.setText("Mensaje: " + ex.getMessage());
        }
    }
}
