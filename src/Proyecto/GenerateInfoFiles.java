package Proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateInfoFiles {

    public static void main(String[] args) {
        try {
            createProductsFile(10, "ProductsData");
            createSalesManInfoFile(10, "SalesManInfoData");
            createSalesMenFile(10, "SalesMenData");
            
            System.out.println("Archivos generados con éxito.");
        } catch (IOException e) {
            System.err.println("Error al generar los archivos: " + e.getMessage());
        }
    }

    public static Integer obtenerCCAleatorio() {
        String filePath = "SalesManInfoData.txt";
        List<Integer> ccList = new ArrayList<>();

        // Leer el archivo y almacenar los CC en una lista
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Dividir la línea por el carácter ';' y obtener el CC
                String[] parts = line.split(";");
                if (parts.length > 1) {
                    try {
                        int cc = Integer.parseInt(parts[1].trim()); // Convertir el CC a entero
                        ccList.add(cc); // Agregar el CC a la lista
                    } catch (NumberFormatException e) {
                        System.err.println("Formato de CC inválido en la línea: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return 1000; // Retornar 1000 en caso de error
        }

        // Generar un CC aleatorio de la lista
        if (!ccList.isEmpty()) {
            Random random = new Random();
            return ccList.get(random.nextInt(ccList.size()));
        }

        return 1000; // Retornar 1000 si no se encontraron CC en la lista
    }

    private static void createSalesMenFile(int salesCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            Random random = new Random();
            for (int i = 0; i < salesCount; i++) {
                // int randomId = 1000 + random.nextInt(10);
                int randomId = obtenerCCAleatorio();
                // int id = 1000 + i;
                writer.write("CC;" + randomId + "\n");
                for (int j = 1; j <= 2; j++) {
                    String productInfo = nameRandom.generateRandomProducts(random.nextInt(nameRandom.products.length));
                    writer.write("Producto" + j + ";" + productInfo + "\n");
                }
            }
        }
    }

    private static void createProductsFile(int productCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            for (int i = 0; i < productCount; i++) {
                String[] product = nameRandom.products[i % nameRandom.products.length];
                writer.write(String.join(";", product) + "\n");
            }
        }
    }

    private static void createSalesManInfoFile(int salesManCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            for (int i = 0; i < salesManCount; i++) {
                String name = nameRandom.generateRandomName();
                writer.write("CC;" + (1000 + i) + ";" + name + "\n");
            }
        }
    }
}
