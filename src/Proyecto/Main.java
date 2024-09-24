package Proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Main class that handles reading product and seller data, and generating sales reports.
public class Main {

    public static void main(String[] args) {
        try {
            // Read the list of products from a file
            List<Product> products = readProducts("ProductsData.txt");
            // Read the list of sellers and their sales from a file
            List<Seller> sellers = readSalesSellers("SalesMenData.txt", products);
            // Sort sellers by total sales in descending order
            sellers.sort((s1, s2) -> Double.compare(s2.getTotalSales(), s1.getTotalSales()));
            // Generate a sales report for sellers
            generateSellerReport(sellers, "SalesReport.csv");
            // Generate a product sold report
            generateProductReport(products, "ProductReport.csv");

        } catch (IOException e) {
            // Handle errors in case of I/O problems
            System.err.println("Error: " + e.getMessage());
        }
    }
    // Reads the data of the salesmen and their sales from the file 
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
                    if (!quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            Seller seller = findOrCreateSeller(sellers, documentType, documentNumber);
                            double saleAmount = getSaleAmountById(products, productId) * quantity;
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

    // The price of a product is obtained from the product ID.
    private static double getSaleAmountById(List<Product> products, String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product.getPrice();
            }
        }
        return 0; // Return 0 if the product is not found
    }
    
    // Increase in the quantity of products sold
    private static void incrementProductSoldQuantity(List<Product> products, String productId, int quantity) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.incrementSoldQuantity(quantity);
                break;
            }
        }
    }

    // The seller is searched for in the list
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

    // Lee los datos de productos desde el archivo
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

    //Generates the sales report of the salesmen and saves the information in the SelesReport.csv file.
    public static void generateSellerReport(List<Seller> sellers, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Seller seller : sellers) {
                writer.write(seller.getDocumentNumber() + ";" + seller.getTotalQuantity() + ";" + seller.getTotalSales() + "\n");
            }
            System.out.println("Seller report generated: " + fileName);
        } catch (IOException e) {
            System.err.println("Error generating seller report: " + e.getMessage());
        }
    }

    // Generates report of products sold and saves the information in the ProductReport.csv file.
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

    // Class representing a seller with their document type, document number, total sales, and total quantity sold.
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
            this.totalSales += amount; // Add the sale amount
        }

        public void incrementTotalQuantity(int quantity) {
            this.totalQuantity += quantity; // Add the sold quantity
        }
    }

    // Class representing a product with its ID, name, price, and sold quantity.
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
            this.soldQuantity += quantity; // Increment sold
        }
    }
}
