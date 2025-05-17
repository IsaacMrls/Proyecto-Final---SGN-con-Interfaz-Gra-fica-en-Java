
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import models.Empleado;

public class VerHistorialEliminacionesFrame extends JFrame {
    public VerHistorialEliminacionesFrame() {
        setTitle("Historial de Eliminaciones");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);

        add(new JLabel("Empleados eliminados:", JLabel.CENTER), BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        Stack<Empleado> historial = gui.EliminarEmpleadoFrame.historialEliminaciones;
        StringBuilder sb = new StringBuilder();
        if (historial.isEmpty()) {
            sb.append("No hay empleados eliminados.");
        } else {
            for (int i = historial.size() - 1; i >= 0; i--) {
                Empleado emp = historial.get(i);
                sb.append(String.format("ID: %s | Nombre: %s | Puesto: %s | Departamento: %s | Sueldo: $%.2f\n",
                    emp.id, emp.nombre, emp.puesto, emp.departamento, emp.calcularSueldo()));
            }
        }

        area.setText(sb.toString());

        setVisible(true);
    }
}
