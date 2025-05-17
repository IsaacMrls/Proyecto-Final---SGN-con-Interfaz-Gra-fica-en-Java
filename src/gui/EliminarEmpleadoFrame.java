
package gui;

import java.awt.*;
import java.util.Stack;
import javax.swing.*;
import models.Empleado;
import models.ListaEmpleados;

public class EliminarEmpleadoFrame extends JFrame {
    private ListaEmpleados lista;
    private JTextField idField;

    // Pila de historial de eliminaciones
    public static Stack<Empleado> historialEliminaciones = new Stack<>();

    public EliminarEmpleadoFrame(ListaEmpleados lista) {
        this.lista = lista;

        setTitle("Eliminar Empleado");
        setSize(350, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("ID del empleado:"));
        idField = new JTextField(15);
        inputPanel.add(idField);

        JButton eliminarBtn = new JButton("Eliminar");
        add(inputPanel);
        add(eliminarBtn);

        eliminarBtn.addActionListener(e -> eliminarEmpleado());

        setVisible(true);
    }

    private void eliminarEmpleado() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID.");
            return;
        }

        Empleado emp = lista.buscarPorID(id);
        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
        "¿Está seguro que desea eliminar al empleado con ID " + id +
        "?\nEsta acción no se puede deshacer.",
        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);


        if (confirm == JOptionPane.YES_OPTION) {
            lista.eliminar(id);
            historialEliminaciones.push(emp); // Agregar a la pila
            lista.guardarEnArchivo();         // Guardar lista actualizada
            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
            dispose();
        }
    }
}
