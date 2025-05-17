# Sistema de Gestión de Empleados - Proyecto Java

Este proyecto es una aplicación de escritorio desarrollada en **Java con Swing**, que permite gestionar empleados mediante operaciones CRUD, utilizando estructuras de datos como listas enlazadas, pilas y colas. La persistencia se realiza mediante archivos `.txt`.

## Características

- **Login y registro de administradores**
- **CRUD de empleados**: registrar, consultar, actualizar y eliminar
- **Historial de eliminaciones**: usando una pila (LIFO)
- **Cola de ediciones pendientes**: usando una cola (FIFO)
- **Exportación de nómina a archivo de texto**
- **Interfaz gráfica** usando Java Swing

## Estructura del Proyecto

```
ProyectoJava/
│
├── data/                         # Archivos de datos (empleados, admins, nómina)
│   ├── admins.txt
│   ├── empleados.txt
│   └── nomina_exportada.txt
│
├── src/
│   ├── Main.java                 # Punto de entrada del programa
│   ├── auth/                    # Autenticación de administradores
│   ├── gui/                     # Interfaces gráficas
│   ├── models/                  # Clases de lógica de negocio y estructuras
│   └── utils/                   # Utilidades para manejo de archivos
```

## Requisitos

- JDK 8 o superior
- IDE recomendado: NetBeans, IntelliJ o Eclipse

## Compilación y Ejecución

### Desde línea de comandos

1. Navega a la carpeta del proyecto:
   ```bash
   cd ProyectoJava/src
   ```

2. Compila todos los archivos `.java`:
   ```bash
   javac -d ../bin */*.java gui/*.java
   ```

3. Ejecuta el programa desde la clase principal:
   ```bash
   java -cp ../bin Main
   ```

### Desde un IDE

1. Importa el proyecto como proyecto Java.
2. Asegúrate de que `Main.java` esté configurado como clase principal.
3. Ejecuta el proyecto.

## Autores

- Luis Alfredo Durán (Practicante)
- Proyecto académico con fines de práctica de estructuras de datos

## Licencia

Este proyecto es de uso académico y libre distribución con fines educativos.