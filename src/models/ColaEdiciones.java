package models;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class ColaEdiciones {
    private Queue<String> cola;

    public ColaEdiciones() {
        cola = new LinkedList<>();
    }

    public void agregar(String id) {
        cola.add(id);
    }

    public List<String> obtenerCola() {
        return new LinkedList<>(cola);
    }
}