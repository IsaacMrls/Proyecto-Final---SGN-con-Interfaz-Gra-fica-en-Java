
package gui;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import models.Empleado;
import models.ListaEmpleados;





public class ActualizarEmpleadoFrame extends JFrame {
    private ListaEmpleados lista;
    private JTextField idBuscarField;
    private JTextField nombreField, edadField, direccionField, telefonoField, puestoField, departamentoField, horasField, costoField;
    private JComboBox<String> sexoBox;
    private Empleado empleadoActual;

    // Cola de ediciones
    public static Queue<String> colaEdicion = new LinkedList<>();

    public ActualizarEmpleadoFrame(ListaEmpleados lista) {
        this.lista = lista;

        setTitle("Actualizar Empleado");
        setSize(400, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(11, 2));

        idBuscarField = new JTextField();

        idBuscarField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || idBuscarField.getText().length() >= 6) {
            e.consume(); // bloquea letras o si ya hay 6 caracteres
                }
            }
        });

        JButton buscarBtn = new JButton("Buscar");

        add(new JLabel("ID del empleado:")); add(idBuscarField);
        add(new JLabel("")); add(buscarBtn);

        nombreField = new JTextField();
        nombreField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            e.consume(); // bloquea cualquier cosa que no sea letra o espacio
                }
            }
        });

        edadField = new JTextField();
        edadField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || edadField.getText().length() >= 2) {
            e.consume(); // bloquea letras, símbolos y más de 2 dígitos
                }
            }
        });


        sexoBox = new JComboBox<>(new String[]{"Hombre", "Mujer"});
        direccionField = new JTextField();
        telefonoField = new JTextField();
        telefonoField = new JTextField();
        ((AbstractDocument) telefonoField.getDocument()).setDocumentFilter(new DocumentFilter() {
            private String digitsOnly(String text) {
                return text.replaceAll("\\D", ""); // quita todo lo que no sea dígito
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
            e.consume(); // bloquea si no es letra ni espacio
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
            e.consume(); // bloquea todo lo que no sea número
                }
            }
        });


        costoField = new JTextField();
        costoField.setHorizontalAlignment(SwingConstants.RIGHT);
        costoField.setText("$0.00");

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setMinimumFractionDigits(2);

        costoField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent e) {
        try {
            String raw = costoField.getText().replaceAll("[$,]", "");
            double value = Double.parseDouble(raw);
            costoField.setText(currencyFormat.format(value));
        } catch (NumberFormatException ex) {
            costoField.setText("$0.00");
                }
            }
        });


        add(new JLabel("Nombre completo:")); add(nombreField);
        add(new JLabel("Edad:")); add(edadField);
        add(new JLabel("Sexo:")); add(sexoBox);
        add(new JLabel("Dirección:")); add(direccionField);
        add(new JLabel("Teléfono:")); add(telefonoField);
        add(new JLabel("Puesto:")); add(puestoField);
        add(new JLabel("Departamento:")); add(departamentoField);
        add(new JLabel("Horas trabajadas:")); add(horasField);
        add(new JLabel("Costo por hora:")); add(costoField);

        JButton actualizarBtn = new JButton("Actualizar");
        add(new JLabel("")); add(actualizarBtn);

        buscarBtn.addActionListener(e -> buscarEmpleado());
        actualizarBtn.addActionListener(e -> confirmarYActualizar());

        setVisible(true);
    }

    private void buscarEmpleado() {
        String id = idBuscarField.getText().trim();
        Empleado emp = lista.buscarPorID(id);
        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
            return;
        }

        this.empleadoActual = emp;
        nombreField.setText(emp.nombre);
        edadField.setText(String.valueOf(emp.edad));
        sexoBox.setSelectedItem(emp.sexo);
        direccionField.setText(emp.direccion);
        telefonoField.setText(emp.telefono);
        puestoField.setText(emp.puesto);
        departamentoField.setText(emp.departamento);
        horasField.setText(String.valueOf(emp.horas));
        costoField.setText(String.valueOf(emp.costoHora));
    }

  private void confirmarYActualizar() {
    if (empleadoActual == null) {
        JOptionPane.showMessageDialog(this, "Empleado No Encontrado.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "¿Desea actualizar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;

    // Agregar ID a la cola de ediciones
    colaEdicion.add(empleadoActual.id);

    // Obtener datos del formulario
    String id = idBuscarField.getText().trim(); // o idBuscarField si estás buscando
    String nombre = nombreField.getText().trim();
    String edadStr = edadField.getText().trim();
    String sexo = (String) sexoBox.getSelectedItem();
    String direccion = direccionField.getText().trim();
    String telefono = telefonoField.getText().trim();
    String puesto = puestoField.getText().trim();
    String departamento = departamentoField.getText().trim();
    String horasStr = horasField.getText().trim();
    String costoStr = costoField.getText().replace(",", "").replace("$", "");

    // Validaciones
    if (nombre.isEmpty() || edadStr.isEmpty() || direccion.isEmpty() ||
        telefono.isEmpty() || puesto.isEmpty() || departamento.isEmpty() ||
        horasStr.isEmpty() || costoStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
        return;
    }

    // o idBuscarField
    if (!id.matches("\\d{1,6}")) {
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

    String telefonoDigits = telefono.replaceAll("\\D", "");
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

    float costo;
    try {
        costo = Float.parseFloat(costoStr);
        if (costo <= 0) {
            JOptionPane.showMessageDialog(this, "El costo por hora debe ser mayor que cero.");
            return;
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Costo por hora inválido.");
        return;
    }

    // Aplicar cambios al empleado
    empleadoActual.nombre = nombre;
    empleadoActual.edad = edad;
    empleadoActual.sexo = sexo;
    empleadoActual.direccion = direccion;
    empleadoActual.telefono = telefono;
    empleadoActual.puesto = puesto;
    empleadoActual.departamento = departamento;
    empleadoActual.horas = Integer.parseInt(horasStr);
    empleadoActual.costoHora = costo;

    lista.guardarEnArchivo();
    JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
    dispose();
}}

