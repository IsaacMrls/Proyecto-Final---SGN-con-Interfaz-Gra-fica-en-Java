
package utils;

import java.io.*;
import java.util.*;

public class FileUtils {
    private static final String CRED_FILE = "data/admins.txt";

    public static HashMap<String, String> loadCredentials() {
        HashMap<String, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CRED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.err.println("Error cargando credenciales.");
        }
        return map;
    }

    public static void saveCredentials(HashMap<String, String> data) {
        try {
            File file = new File(CRED_FILE);
            file.getParentFile().mkdirs(); // Asegura que el directorio exista

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String k : data.keySet()) {
                    writer.write(k + "," + data.get(k));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error guardando credenciales.");
            e.printStackTrace(); // Mostrar detalles del error
        }
    }
}
