package models;

public class Empleado {
    public String id;
    public String nombre;
    public int edad;
    public String sexo;
    public String direccion;
    public String telefono;
    public String puesto;
    public String departamento;
    public int horas;
    public float costoHora;

    public Empleado siguiente;

    public Empleado(String id, String nombre, int edad, String sexo, String direccion,
                    String telefono, String puesto, String departamento, int horas, float costoHora) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.puesto = puesto;
        this.departamento = departamento;
        this.horas = horas;
        this.costoHora = costoHora;
        this.siguiente = null;
    }

    public float calcularSueldo() {
        return horas * costoHora;
    }

    public String toCSV() {
        return id + "," + nombre + "," + edad + "," + sexo + "," + direccion + "," + telefono + "," +
               puesto + "," + departamento + "," + horas + "," + costoHora;
    }
}
