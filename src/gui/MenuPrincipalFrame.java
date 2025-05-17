
package gui;

import java.awt.*;
import javax.swing.*;
import models.ListaEmpleados;






public class MenuPrincipalFrame extends JFrame {
    private ListaEmpleados listaEmpleados = new ListaEmpleados();

    public MenuPrincipalFrame() {
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1));

        add(new JLabel("Menú de opciones", JLabel.CENTER));

        JButton registrarBtn = new JButton("Registrar nuevo empleado");
        JButton mostrarBtn = new JButton("Mostrar nómina");
        JButton actualizarBtn = new JButton("Actualizar datos");
        JButton eliminarBtn = new JButton("Eliminar empleado");
        JButton consultarBtn = new JButton("Consultar Empleado");
        JButton historialBtn = new JButton("Ver historial de eliminaciones");
        JButton colaBtn = new JButton("Ver cola de ediciones");
        JButton cerrarBtn = new JButton("Cerrar sesión");

        add(registrarBtn);
        add(mostrarBtn);
        add(actualizarBtn);
        add(eliminarBtn);
        add(consultarBtn);
        add(historialBtn);
        add(colaBtn);
        add(cerrarBtn);

        registrarBtn.addActionListener(e -> {
            new RegistrarEmpleadoFrame(listaEmpleados);
        });

        mostrarBtn.addActionListener(e -> {
            new MostrarNominaFrame(listaEmpleados);
        });

        actualizarBtn.addActionListener(e -> {
        new ActualizarEmpleadoFrame(listaEmpleados);
        });


        colaBtn.addActionListener(e -> {
            new VerColaEdicionesFrame();
        });

        eliminarBtn.addActionListener(e -> {
        new EliminarEmpleadoFrame(listaEmpleados);
        });

        historialBtn.addActionListener(e -> {
        new VerHistorialEliminacionesFrame();
        });

        consultarBtn.addActionListener(e -> {
        new ConsultarEmpleadoFrame(listaEmpleados);
        });

        cerrarBtn.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(this,
        "¿Está seguro que desea cerrar sesión?",
        "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
        dispose();  // Cierra la ventana actual
        new LoginFrame(); // Vuelve al login
         }
        });



        setVisible(true);

       
        

    }
}
