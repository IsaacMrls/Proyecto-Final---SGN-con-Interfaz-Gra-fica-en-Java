
package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Empleado;
import models.ListaEmpleados;

public class MostrarNominaFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ListaEmpleados lista;
    private JTextField searchField;

    public MostrarNominaFrame(ListaEmpleados lista) {
        this.lista = lista;

        setTitle("Mostrar Nómina");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Campo de búsqueda por nombre
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        topPanel.add(new JLabel("Buscar por nombre:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Puesto", "Departamento", "Teléfono", "Horas", "Costo/Hora", "Sueldo"};
        tableModel = new DefaultTableModel(columnas, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botón de exportar
        JButton exportBtn = new JButton("Exportar a TXT");
        add(exportBtn, BorderLayout.SOUTH);

        // Cargar todos los empleados al iniciar
        cargarEmpleados(lista.listar());

        // Filtro por nombre
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = searchField.getText().toLowerCase();
                List<Empleado> filtrados = lista.listar().stream()
                        .filter(emp -> emp.nombre.toLowerCase().contains(texto))
                        .collect(Collectors.toList());
                cargarEmpleados(filtrados);
            }
        });

        // Exportar
        exportBtn.addActionListener(e -> exportarATxt());

        setVisible(true);
    }

    private void cargarEmpleados(List<Empleado> empleados) {
        tableModel.setRowCount(0); // Limpiar tabla
        for (Empleado e : empleados) {
            Object[] fila = {
                e.id,
                e.nombre,
                e.puesto,
                e.departamento,
                e.telefono,
                e.horas,
                e.costoHora,
                String.format("$%.2f", e.calcularSueldo())
            };
            tableModel.addRow(fila);
        }
    }

    private void exportarATxt() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/nomina_exportada.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String linea = String.format("ID: %s | Nombre: %s | Puesto: %s | Sueldo: %s",
                        tableModel.getValueAt(i, 0),
                        tableModel.getValueAt(i, 1),
                        tableModel.getValueAt(i, 2),
                        tableModel.getValueAt(i, 7));
                bw.write(linea);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Nómina exportada correctamente a data/nomina_exportada.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar archivo.");
            ex.printStackTrace();
        }
    }
}
