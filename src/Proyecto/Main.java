package Proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<Product> products = readProducts("ProductsData.txt");
            List<Seller> sellers = readSalesSellers("SalesMenData.txt", products);
            sellers.sort((s1, s2) -> Double.compare(s2.getTotalSales(), s1.getTotalSales()));
            generateSellerReport(sellers, "SalesReport.csv");
            generateProductReport(products, "ProductReport.csv");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static List<Seller> readSalesSellers(String fileName, List<Product> products) throws IOException {
        List<Seller> sellers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 4) {
                    String documentType = data[0];
                    String documentNumber = data[1];
                    String productId = data[2];
                    String quantityStr = data[3].trim().replace(",", ".");
                    
                    // Sugerencia 1: Validar que el producto existe antes de continuar
                    if (getSaleAmountById(products, productId) == 0) {
                        System.err.println("Producto no encontrado: " + productId);
                        continue; // Saltar esta línea si el producto no existe
                    }
                    
                    if (!quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            // Agrega validacion de valores negativos
                            if (quantity < 0) {
                                System.err.println("Negative quantity found for product ID: " + productId);
                                continue;  // Saltar la línea si la cantidad es negativa
                            }
                            Seller seller = findOrCreateSeller(sellers, documentType, documentNumber);
                            double saleAmount = getSaleAmountById(products, productId) * quantity;
                            // Agrega validacion de valores negativos
                            if (saleAmount < 0) {
                                System.err.println("Negative price for product ID: " + productId);
                                continue;  // Saltar la línea si el precio es negativo
                            }
                            seller.incrementTotalSales(saleAmount);
                            seller.incrementTotalQuantity(quantity);
                            incrementProductSoldQuantity(products, productId, quantity);
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing quantity in line: " + line);
                        }
                    } else {
                        System.err.println("Empty quantity for seller: " + documentNumber);
                    }
                } else {
                    System.err.println("Malformed or incomplete line: " + line);
                }
            }
        }
        return sellers;
    }

    private static double getSaleAmountById(List<Product> products, String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product.getPrice();
            }
        }
        // Sugerencia 2: Imprimir un mensaje si el producto no es encontrado
        System.err.println("Product with ID " + productId + " not found");
        return 0;
    }

    private static void incrementProductSoldQuantity(List<Product> products, String productId, int quantity) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.incrementSoldQuantity(quantity);
                return; // Sugerencia 3: Usar `return` en lugar de `break` para salir directamente del método
            }
        }
    }

    private static Seller findOrCreateSeller(List<Seller> sellers, String documentType, String documentNumber) {
        for (Seller seller : sellers) {
            if (seller.getDocumentType().equals(documentType) && seller.getDocumentNumber().equals(documentNumber)) {
                return seller;
            }
        }
        Seller newSeller = new Seller(documentType, documentNumber);
        sellers.add(newSeller);
        return newSeller;
    }

    public static List<Product> readProducts(String fileName) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(";");
                    if (data.length == 3 && !data[2].trim().isEmpty()) {
                        try {
                            String id = data[0].trim();
                            String name = data[1].trim();
                            double price = Double.parseDouble(data[2].trim().replace(",", "."));
                            products.add(new Product(id, name, price));
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing price in line: " + line);
                        }
                    } else {
                        System.err.println("Malformed or incomplete line: " + line);
                    }
                }
            }
        }
        return products;
    }

    public static void generateSellerReport(List<Seller> sellers, String fileName) throws IOException {
        // Sugerencia 4: Se puede usar BufferedWriter para mejorar el rendimiento al escribir archivos grandes.
    	// try (BufferedWriter writer = new FileWriter(fileName)) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Seller seller : sellers) {
                writer.write(seller.getDocumentNumber() + ";" + seller.getTotalQuantity() + ";" + seller.getTotalSales() + "\n");
            }
            System.out.println("Seller report generated: " + fileName);
        } catch (IOException e) {
            System.err.println("Error generating seller report: " + e.getMessage());
        }
    }

    public static void generateProductReport(List<Product> products, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Product product : products) {
                writer.write(product.getName() + ";" + product.getSoldQuantity() + "\n");
            }
            System.out.println("Product report generated: " + fileName);
        } catch (IOException e) {
            System.err.println("Error generating product report: " + e.getMessage());
        }
    }

    // Seller Class
    static class Seller {
        private String documentType;
        private String documentNumber;
        private double totalSales;
        private int totalQuantity;

        public Seller(String documentType, String documentNumber) {
            this.documentType = documentType;
            this.documentNumber = documentNumber;
            this.totalSales = 0;
            this.totalQuantity = 0;
        }

        public String getDocumentType() {
            return documentType;
        }

        public String getDocumentNumber() {
            return documentNumber;
        }

        public double getTotalSales() {
            return totalSales;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public void incrementTotalSales(double amount) {
            this.totalSales += amount; // Add the monetary amount
        }

        public void incrementTotalQuantity(int quantity) {
            this.totalQuantity += quantity; // Add the quantity sold
        }
    }

    // Product Class
    static class Product {
        private String id;
        private String name;
        private double price;
        private int soldQuantity;

        public Product(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.soldQuantity = 0;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getSoldQuantity() {
            return soldQuantity;
        }

        public void incrementSoldQuantity(int quantity) {
            this.soldQuantity += quantity;
        }
    }
}
