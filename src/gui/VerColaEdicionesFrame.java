
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class VerColaEdicionesFrame extends JFrame {
    public VerColaEdicionesFrame() {
        setTitle("Cola de Ediciones");
        setSize(300, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);

        add(new JLabel("IDs en proceso de edición:", JLabel.CENTER), BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        StringBuilder sb = new StringBuilder();
        Queue<String> cola = gui.ActualizarEmpleadoFrame.colaEdicion;
        if (cola.isEmpty()) {
            sb.append("La cola está vacía.");
        } else {
            for (String id : cola) {
                sb.append("- ").append(id).append("\n");
            }
        }

        area.setText(sb.toString());

        setVisible(true);
    }
}
