package Proyecto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

    public static void main(String[] args) {
        try {
            createSalesMenInfoFile(10, "SalesMenData", 1000);
            createProductsFile(10, "ProductsData");
            createSalesManInfoFile(10, "SalesManInfoData");
            
            System.out.println("Archivos generados con éxito.");
        } catch (IOException e) {
            System.err.println("Error al generar los archivos: " + e.getMessage());
        }
    }

    private static void createSalesMenInfoFile(int salesCount, String fileName, long id) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            Random random = new Random();
            for (int i = 0; i < salesCount; i++) {
                writer.write("CC;" + id + "\n");
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
