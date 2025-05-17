package gui;

import auth.AdminAuth;
import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login Administrador");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Iniciar Sesión");
        JButton registerBtn = new JButton("Registrarse");

        add(new JLabel("Correo:"));
        add(emailField);
        add(new JLabel("Contraseña:"));
        add(passwordField);
        add(loginBtn);
        add(registerBtn);

        AdminAuth auth = new AdminAuth();

        loginBtn.addActionListener(e -> {

            String correo = emailField.getText().trim();
            String contrasena = new String(passwordField.getPassword());

            if (correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Correo y contraseña son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!correo.matches("^[\\w.-]+@[\\w-]+\\.[a-zA-Z]{2,}$"))
 {
                JOptionPane.showMessageDialog(this, "Correo electrónico inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (auth.login(emailField.getText(), new String(passwordField.getPassword()))) {
                new MenuPrincipalFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        });

        registerBtn.addActionListener(e -> {
            new RegistroFrame();
            dispose();
        });

        setVisible(true);
    }
}