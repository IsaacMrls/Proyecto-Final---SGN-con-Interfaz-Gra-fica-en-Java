package models;

import java.util.*;
import java.io.*;

public class ListaEmpleados {
    private Empleado cabeza;

    public ListaEmpleados() {
        this.cabeza = null;
        cargarDesdeArchivo();
    }

    public void agregarOrdenado(Empleado emp) {
        if (cabeza == null || emp.nombre.compareToIgnoreCase(cabeza.nombre) < 0) {
            emp.siguiente = cabeza;
            cabeza = emp;
            return;
        }
        Empleado actual = cabeza;
        while (actual.siguiente != null && emp.nombre.compareToIgnoreCase(actual.siguiente.nombre) > 0) {
            actual = actual.siguiente;
        }
        emp.siguiente = actual.siguiente;
        actual.siguiente = emp;
    }

    public Empleado buscarPorID(String id) {
        Empleado actual = cabeza;
        while (actual != null) {
            if (actual.id.equals(id)) return actual;
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean eliminar(String id) {
        if (cabeza == null) return false;
        if (cabeza.id.equals(id)) {
            cabeza = cabeza.siguiente;
            return true;
        }
        Empleado actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.id.equals(id)) {
                actual.siguiente = actual.siguiente.siguiente;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        Empleado actual = cabeza;
        while (actual != null) {
            lista.add(actual);
            actual = actual.siguiente;
        }
        return lista;
    }

    public void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter("data/empleados.txt")) {
            for (Empleado emp : listar()) {
                pw.println(String.join(",", emp.id, emp.nombre, String.valueOf(emp.edad), emp.sexo,
                        emp.direccion, emp.telefono, emp.puesto, emp.departamento,
                        String.valueOf(emp.horas), String.valueOf(emp.costoHora)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo() {
        File archivo = new File("data/empleados.txt");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                if (p.length == 10) {
                    Empleado emp = new Empleado(p[0], p[1], Integer.parseInt(p[2]), p[3], p[4], p[5],
                            p[6], p[7], Integer.parseInt(p[8]), Float.parseFloat(p[9]));
                    agregarOrdenado(emp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}