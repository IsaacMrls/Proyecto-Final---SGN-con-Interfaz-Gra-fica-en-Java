package models;

import java.util.Stack;
import java.util.List;

public class PilaEliminados {
    private Stack<Empleado> pila;

    public PilaEliminados() {
        pila = new Stack<>();
    }

    public void push(Empleado emp) {
        pila.push(emp);
    }

    public List<Empleado> getHistorial() {
        return pila;
    }
}