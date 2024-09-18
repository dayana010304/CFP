package Proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateInfoFiles2 {

    private static final String SALES_MAN_INFO_FILE = "SalesManInfoData.txt";
    private static final int DEFAULT_CC = 1000;

    public static void main(String[] args) {
        try {
            // Crea los archivos con los datos de productos, información de vendedores y ventas
            createFile(9, "ProductsData", nameRandom::generateRandomProducts);
            createFile(4, "SalesManInfoData", nameRandom::idNameList);
            createFile(8, "SalesMenData", (i) -> nameRandom.quantityProductsSelled(i, i % nameRandom.products.length));

        } catch (IOException e) {
            System.err.println("Error al generar los archivos: " + e.getMessage());
        }
    }

    // Método para obtener un CC aleatorio del archivo
    public static Integer getCCRandom() {
        List<Integer> ccList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SALES_MAN_INFO_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 1) {
                    try {
                        int cc = Integer.parseInt(parts[1].trim());
                        ccList.add(cc);
                    } catch (NumberFormatException e) {
                        System.err.println("Formato de CC inválido en la línea: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return DEFAULT_CC;
        }

        // Retorna un CC aleatorio de la lista, o un valor por defecto si la lista está vacía
        return ccList.isEmpty() ? DEFAULT_CC : getRandomElement(ccList);
    }

    // Método genérico para crear un archivo con la lógica de generación de datos proporcionada
    private static void createFile(int count, String fileName, DataGenerator generator) throws IOException {
        String idFile = fileName + ".txt";
        int limit = Math.min(count, nameRandom.products.length);

        // Escribir los datos generados en el archivo
        try (FileWriter writer = new FileWriter(idFile)) {
            for (int i = 0; i < limit; i++) {
                String data = generator.generate(i);
                writer.write(data + "\n");
            }
            System.out.println("Archivo " + idFile + " generado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
            throw e;
        }
    }

    // Método auxiliar para obtener un elemento aleatorio de una lista
    private static <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    // Interfaz funcional para generalizar la lógica de generación de datos
    @FunctionalInterface
    interface DataGenerator {
        String generate(int index);
    }
}
