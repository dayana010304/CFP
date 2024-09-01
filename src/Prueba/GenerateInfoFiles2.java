package Prueba;

import java.io.IOException;
import java.io.FileWriter;
import java.util.Random;

public class GenerateInfoFiles2 {

	public static void main (String[]arg) {
		try {
			CreateSalesMenInfoFile (10, "SalesMenData", 1000);
			CreateProductsFile (20, "ProductsData");
			CreateSalesManInfoFile (10, "SalesManInfoData");
			
			System.out.println("Archivos generados con exito");
		} catch (IOException e) {
			System.err.println("Error al generar los archivos: " + e.getMessage());
		}
	}

	public static void CreateSalesMenInfoFile(int randomSalesCount, String name, long id) throws IOException {
			Random random = new Random ();
			try (FileWriter writer = new FileWriter(name + ".txt")){
				for (int i = 0; i < randomSalesCount; i++) {
					writer.write("Type" + ";" + id + "\n");
					for (int j = 1; j <= 3; j++) {
						writer.write("Producto" + j + ";" + (random.nextInt(10) + 1) + "\n");
				}
			}
		}
	}

	public static void CreateProductsFile(int productsCount, String name) throws IOException {
		try (FileWriter writer = new FileWriter(name + ".txt")){
			for (int i = 1; i <= productsCount; i++) {
				writer.write("IdProduct" + i + ";ProductName" + i + ";" + (i*10) + "\n");
			}
		}
	}

	public static void CreateSalesManInfoFile(int salesmanCount, String name) throws IOException{
		Random random = new Random();
		String [] firstNames = {"John", "Juan", "Tomas"};
		String [] lastNames = {"Smith", "Torres", "Taborda"};
		try (FileWriter writer = new FileWriter (name + ".txt")){
			for (int i = 1; i <= salesmanCount; i ++) {
				String firstName = firstNames[random.nextInt(firstNames.length)];
				String lastName = lastNames[random.nextInt(lastNames.length)];
				writer.write("Type" + ";" + (1000 + i) + ";" + firstName + " " + lastName + "\n");
			}
		}
	}
}
