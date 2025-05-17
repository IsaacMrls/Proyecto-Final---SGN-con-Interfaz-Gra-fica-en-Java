
package gui;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import models.Empleado;
import models.ListaEmpleados;



public class RegistrarEmpleadoFrame extends JFrame {
    private JTextField idField, nombreField, edadField, direccionField, telefonoField,
            puestoField, departamentoField, horasField, costoField;
    private JComboBox<String> sexoBox;
    private ListaEmpleados lista;

    public RegistrarEmpleadoFrame(ListaEmpleados lista) {
        this.lista = lista; 

        setTitle("Registrar nuevo empleado");
        setSize(400, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(11, 2));

        idField = new JTextField();
        idField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || idField.getText().length() >= 6) {
            e.consume();
                }
            }
        });

        nombreField = new JTextField();
        nombreField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            e.consume();  // Bloquear si no es letra ni espacio
                }
            }
        });

        edadField = new JTextField();
        edadField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || edadField.getText().length() >= 2) {
        e.consume(); // Bloquea si no es número o si ya hay 2 caracteres
                }
            }
        });

        sexoBox = new JComboBox<>(new String[]{"Hombre", "Mujer"});

        direccionField = new JTextField();

        telefonoField = new JTextField();
        ((AbstractDocument) telefonoField.getDocument()).setDocumentFilter(new DocumentFilter() {
        private String digitsOnly(String text) {
        return text.replaceAll("\\D", "");
        }

        private String formatPhone(String digits) {
        if (digits.length() > 10) digits = digits.substring(0, 10);
        StringBuilder formatted = new StringBuilder();

        if (digits.length() >= 1) formatted.append("(");
        if (digits.length() >= 1) formatted.append(digits.substring(0, Math.min(3, digits.length())));
        if (digits.length() >= 4) formatted.append(") ").append(digits.substring(3, Math.min(6, digits.length())));
        if (digits.length() >= 7) formatted.append(" ").append(digits.substring(6));
        return formatted.toString();
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        String current = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = current.substring(0, offset) + text + current.substring(offset + length);
        String digits = digitsOnly(newText);
        fb.replace(0, fb.getDocument().getLength(), formatPhone(digits), attrs);
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        replace(fb, offset, 0, string, attr);
            }
        });
        puestoField = new JTextField();
        puestoField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            e.consume(); // Bloquea si no es letra o espacio
                }
            }
        });

        departamentoField = new JTextField();
        departamentoField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            e.consume();
                }
            }
        });

        horasField = new JTextField();
        horasField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume(); // Solo permite números
                }
            }
        });

        costoField = new JTextField();
        costoField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        String text = costoField.getText();

        // Permitir solo dígitos, un punto decimal y teclas de borrado
        if (!Character.isDigit(c) && c != '.' && c != '\b') {
            e.consume(); // Bloquea letras u otros caracteres
        }

        // Solo un punto decimal
        if (c == '.' && text.contains(".")) {
            e.consume(); // Ya hay un punto
                }
            }
        });


        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setMinimumFractionDigits(2);

        costoField.setHorizontalAlignment(SwingConstants.RIGHT);
        costoField.setText("$0.00");

        costoField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
        try {
            String raw = costoField.getText().replaceAll("[$,]", "");
            double value = Double.parseDouble(raw);
            costoField.setText(currencyFormat.format(value));
        } catch (NumberFormatException ex) {
            costoField.setText("$0.00");
                }
            }
        });


        JButton registrarBtn = new JButton("Registrar");

        add(new JLabel("ID (6 caracteres):")); add(idField);
        add(new JLabel("Nombre completo:")); add(nombreField);
        add(new JLabel("Edad:")); add(edadField);
        add(new JLabel("Sexo:")); add(sexoBox);
        add(new JLabel("Dirección:")); add(direccionField);
        add(new JLabel("Teléfono:")); add(telefonoField);
        add(new JLabel("Puesto:")); add(puestoField);
        add(new JLabel("Departamento:")); add(departamentoField);
        add(new JLabel("Horas trabajadas:")); add(horasField);
        add(new JLabel("Costo por hora:")); add(costoField);
        add(new JLabel("")); add(registrarBtn);

        registrarBtn.addActionListener(e -> registrarEmpleado());

        setVisible(true);
    }

    private void registrarEmpleado() {
        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String edadStr = edadField.getText().trim();
        String sexo = (String) sexoBox.getSelectedItem();
        String direccion = direccionField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String puesto = puestoField.getText().trim();
        String departamento = departamentoField.getText().trim();
        String horasStr = horasField.getText().trim();
        String costoStr = costoField.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || edadStr.isEmpty() || direccion.isEmpty() ||
            telefono.isEmpty() || puesto.isEmpty() || departamento.isEmpty() ||
            horasStr.isEmpty() || costoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (!id.matches("^\\d{1,6}$")) {
            JOptionPane.showMessageDialog(this, "El ID debe contener solo números (máximo 6 dígitos).");
        return;
        }


        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras.");
            return;
        }

        if (!edadStr.matches("\\d{1,2}")) {
            JOptionPane.showMessageDialog(this, "Edad inválida. Solo números de 2 cifras como máximo.");
            return;
        }

        int edad = Integer.parseInt(edadStr);
        if (edad < 18) {
            JOptionPane.showMessageDialog(this, "El empleado debe tener mínimo 18 años.");
            return;
        }

        String telefonoDigits = telefono.replaceAll("\\D", ""); // elimina todo excepto números

        if (telefonoDigits.length() != 10) {
        JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 10 dígitos.");
        return;
        }


        if (!puesto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El puesto solo debe contener letras.");
            return;
        }

        if (!departamento.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El departamento solo debe contener letras.");
            return;
        }

        if (!horasStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Las horas trabajadas deben ser numéricas.");
            return;
        }

        costoStr = costoStr.replaceAll("[$,]", "");
        float costo = Float.parseFloat(costoStr);



        Empleado nuevo = new Empleado(id, nombre, edad, sexo, direccion, telefono, puesto,
             departamento, Integer.parseInt(horasStr), costo);
        lista.agregarOrdenado(nuevo);
        guardarEnArchivo(nuevo);

        JOptionPane.showMessageDialog(this, "Empleado registrado correctamente.");
        this.dispose();
    }

    private void guardarEnArchivo(Empleado e) {
        try {
            File file = new File("data/empleados.txt");
            file.getParentFile().mkdirs();  // Crea carpeta si no existe
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(e.toCSV());
                bw.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar en archivo.");
            ex.printStackTrace();
        }
    }
}
