package gui;

import auth.AdminAuth;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;


public class RegistroFrame extends JFrame {
    public RegistroFrame() {
        setTitle("Registro Administrador");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        JTextField nameField = new JTextField();
        // Validación: solo letras y espacios para el campo de nombre
        nameField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            e.consume(); // Ignora caracteres que no sean letras o espacio
                }
            }
        });

        JTextField emailField = new JTextField();
        JPasswordField pass1 = new JPasswordField();
        JPasswordField pass2 = new JPasswordField();

        JButton registerBtn = new JButton("Registrar");

        add(new JLabel("Nombre completo:"));
        add(nameField);
        add(new JLabel("Correo:"));
        add(emailField);
        add(new JLabel("Contraseña:"));
        add(pass1);
        add(new JLabel("Confirmar contraseña:"));
        add(pass2);
        add(registerBtn);

        AdminAuth auth = new AdminAuth();

        registerBtn.addActionListener(e -> {

            String nombre = nameField.getText().trim();
            String correo = emailField.getText().trim();
            String contrasena = new String(pass1.getPassword());
            String confirmacion = new String(pass2.getPassword());

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmacion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!correo.matches("^[\\w.-]+@[\\w-]+\\.[a-zA-Z]{2,}$"))
 {
                JOptionPane.showMessageDialog(this, "Correo electrónico inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!contrasena.equals(confirmacion)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String email = emailField.getText();
            String p1 = new String(pass1.getPassword());
            String p2 = new String(pass2.getPassword());
            if (!p1.equals(p2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
                return;
            }
            if (auth.register(email, p1)) {
                JOptionPane.showMessageDialog(this, "Registro exitoso.");
                new LoginFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El correo ya está registrado.");
            }
        });

        setVisible(true);
    }
}