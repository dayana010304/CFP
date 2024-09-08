package Proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//class to generate files
public class GenerateInfoFiles {

	//Main method, entry point of the program
    public static void main(String[] args) {
        try {
            createProductsFile(10, "ProductsData");
            createSalesManInfoFile(10, "SalesManInfoData");
            createSalesMenFile(10, "SalesMenData");
            
            //Printing success message when files are generated successfully
            System.out.println("Files generated successfully.");
        } catch (IOException e) {
        	//Catching and printing error message if any exception occurs during file generation
            System.err.println("Error generating files: " + e.getMessage());
        }
    }

    public static Integer getCCRandom() {
        String filePath = "SalesManInfoData.txt";
        List<Integer> ccList = new ArrayList<>();

        // Read the file and store the CCs in a list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
            	// Divide the line by the ';' character and get the CC
                String[] parts = line.split(";");
                if (parts.length > 1) {
                    try {
                        int cc = Integer.parseInt(parts[1].trim()); // Convert CC to integer
                        ccList.add(cc); // Add the CC to the list
                    } catch (NumberFormatException e) {
                        System.err.println("Formato de CC inválido en la línea: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return 1000; // Return 1000 in case of error
        }

        // Generate a random CC from the list
        if (!ccList.isEmpty()) {
            Random random = new Random();
            return ccList.get(random.nextInt(ccList.size()));
        }

        return 1000; // Return 1000 if no CCs were found in the list
    }
    
    //Method to create salesmen data file
    private static void createSalesMenFile(int randomSalesCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            Random random = new Random();
            for (int i = 0; i < randomSalesCount; i++) {
                // int randomId = 1000 + random.nextInt(10);
                int randomId = getCCRandom();
                // int id = 1000 + i;
                writer.write("CC;" + randomId + "\n");
                for (int j = 1; j <= 2; j++) {
                    String productInfo = nameRandom.generateRandomProducts(random.nextInt(nameRandom.products.length));
                    writer.write("Producto" + j + ";" + productInfo + "\n");
                }
            }
        }
    }
    
    //Method to create products data file
    private static void createProductsFile(int productCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            for (int i = 0; i < productCount; i++) {
                String[] product = nameRandom.products[i % nameRandom.products.length];
                writer.write(String.join(";", product) + "\n");
            }
        }
    }

    // Method to create salesmen info data file
    private static void createSalesManInfoFile(int salesManCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            for (int i = 0; i < salesManCount; i++) {
                String name = nameRandom.generateRandomName();
                writer.write("CC;" + (1000 + i) + ";" + name + "\n");
            }
        }
    }
}
