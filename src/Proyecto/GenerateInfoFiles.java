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

	// Main method, entry point of the program
	public static void main(String[] args) {
		try {
			createProductsFile(9, "ProductsData");
			createSalesManInfoFile(4, "SalesManInfoData");
			createSalesMenFile(8, "SalesMenData");

			// Printing success message when files are generated successfully
			// System.out.println("Files generated successfully.");
		} catch (IOException e) {
			// Catching and printing error message if any exception occurs during file
			// generation
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
						System.err.println("Invalid CC format in the line: " + line);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
			return 1000; // Return 1000 in case of error
		}

		// Generate a random CC from the list
		if (!ccList.isEmpty()) {
			Random random = new Random();
			return ccList.get(random.nextInt(ccList.size()));
		}

		return 1000; // Return 1000 if no CCs were found in the list
	}

	// Method to create salesmen data file
	private static void createSalesMenFile(int randomSalesCount, String fileName) throws IOException {
		String idFile = fileName + ".txt";
		int cantPro = randomSalesCount;
		int values = nameRandom.products.length;
		String dates = "";
		if (cantPro < values) {

			try (FileWriter writer = new FileWriter(fileName + ".txt")) {
				int p = randomSalesCount;
				for (int i = 0; i < nameRandom.numIdEmpleado.length; i++) {
					for (p = 0; p < nameRandom.products.length; p++) {
						dates = nameRandom.quantityProductsSelled(i, p);
						writer.write(dates + "\n");
					}
				}
				System.out.println("Files " + idFile + " generated successfully.");
			}
		} else {
			System.out.println("The file " + fileName + " cannot be created "
					+ "the quantity of products must be less than " + values);
		}
	}

	// Method to create products data file
	private static void createProductsFile(int productCount, String fileName) throws IOException {
		String idFile = fileName + ".txt";
		int cantPro = productCount;
		int values = nameRandom.products.length;
		String dates = "";
		if (cantPro < values) {
			try (FileWriter writer = new FileWriter(fileName + ".txt")) {
				for (int i = 0; i < cantPro; i++) {
					dates = nameRandom.generateRandomProducts(i);
					writer.write(dates + "\n");
				}
				System.out.println("Files " + idFile + " generated successfully.");
			}
		} else {
			System.out.println("The file " + fileName + " cannot be created "
					+ "the quantity of products must be less than " + values);
		}

	}

	// Method to create salesmen info data file
	private static void createSalesManInfoFile(int salesManCount, String fileName) throws IOException {
		String idFile = fileName + ".txt";
		int cantSeller = salesManCount;
		int values = nameRandom.numIdEmpleado.length;
		String dates = "";
		if (cantSeller < values) {
			try (FileWriter writer = new FileWriter(fileName + ".txt")) {
				for (int i = 0; i <= 4; i++) {

					dates = nameRandom.idNameList(i);
					writer.write(dates + "\n");

				}
				System.out.println("Files " + idFile + " generated successfully.");
			}
		} else {
			System.out.println("The file " + fileName + " cannot be created "
					+ "the quantity of products must be less than " + values);
		}
	}
}