
package gui;

import models.Empleado;
import models.ListaEmpleados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultarEmpleadoFrame extends JFrame {
    private ListaEmpleados lista;
    private JTextField idField, nombreField;
    private DefaultTableModel tableModel;

    public ConsultarEmpleadoFrame(ListaEmpleados lista) {
        this.lista = lista;

        setTitle("Consultar Empleado");
        setSize(900, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new GridLayout(2, 3));
        searchPanel.add(new JLabel("Buscar por ID:"));
        idField = new JTextField();
        searchPanel.add(idField);
        JButton buscarBtn = new JButton("Buscar");
        searchPanel.add(buscarBtn);

        searchPanel.add(new JLabel("Buscar por Nombre:"));
        nombreField = new JTextField();
        searchPanel.add(nombreField);
        searchPanel.add(new JLabel("")); // espacio vacío

        add(searchPanel, BorderLayout.NORTH);

        // Tabla de resultados
        String[] columnas = {"ID", "Nombre", "Edad", "Sexo", "Dirección", "Teléfono", "Puesto",
                             "Departamento", "Horas", "Costo/Hora", "Sueldo"};
        tableModel = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(tableModel);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        buscarBtn.addActionListener(e -> buscarEmpleado());

        setVisible(true);
    }

    private void buscarEmpleado() {
        String id = idField.getText().trim().toLowerCase();
        String nombre = nombreField.getText().trim().toLowerCase();

        tableModel.setRowCount(0); // Limpiar tabla

        List<Empleado> resultados;

        if (!id.isEmpty()) {
            Empleado e = lista.buscarPorID(id);
            if (e != null) {
                resultados = List.of(e);
            } else {
                JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
                return;
            }
        } else if (!nombre.isEmpty()) {
            resultados = lista.listar().stream()
                .filter(emp -> emp.nombre.toLowerCase().contains(nombre))
                .collect(Collectors.toList());
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un ID o un nombre para buscar.");
            return;
        }

        for (Empleado e : resultados) {
            tableModel.addRow(new Object[] {
                e.id, e.nombre, e.edad, e.sexo, e.direccion, e.telefono,
                e.puesto, e.departamento, e.horas, e.costoHora, e.calcularSueldo()
            });
        }
    }
}
