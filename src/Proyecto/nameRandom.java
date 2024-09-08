package Proyecto;

import java.util.Random;

public class nameRandom {
	
		private static final String[][] name = {
				{"Carlos", "Andres"},
				{"Laura", "Andrea" },
				{"Raul","Luis"},
				{"Maria","Sofia"},
				{"Pedro", "jose"}
			};

	    private static final String[][] lastName = {
	    		{"Garcia", "Rodriguez"},
    			{"Martinez", "Hernandez"},
    			{"Gonzalez", "Perez"},
				{"Sanchez", "Ramirez"},
				{"Torres", "Flores" }
	    };
	    
	    public static final String [][] numIdEmpleado= {
	    		{"CC", "76148503"},
	    		{"CC", "116495870"},
	    		{"CC", "72148503"},
	    		{"CC", "8645893"},
	    		{"CC", "72689436"},
	    };
	    
       public static String[][] products = {
                {"IDP2000", "Toyota Corolla", "20000"},
                {"IDP2021", "Honda Civic", "22000"},
                {"IDP2032", "Ford Focus", "21000"},
                {"IDP2045", "Chevrolet Malibu", "23000"},
                {"IDP2066", "Nissan Sentra", "19500"},
                {"IDP2078", "Hyundai Elantra", "18000"},
                {"IDP2093", "Volkswagen Jetta", "25000"},
                {"IDP2107", "Mazda 3", "24000"},
                {"IDP2118", "Subaru Impreza", "22500"},
                {"IDP2139", "Kia Forte", "19000"}
            };

	    
	    public static String idNameList (int cantEmpleados) {
	    	String idSeller = "";
	    	String nameSeller = "";
	    	String datos= "";
	    	int i = cantEmpleados;
	    	idSeller = idEmpleado(i);
	    	nameSeller = name[i][0]+ " " + name[i][1]+ ";" + lastName[i][0]+ " " + lastName[i][1]+ ";";
	    	datos = idSeller + nameSeller;
	    	return datos; 
	    }
	    
	    public static String generateRandomProducts(int in) {
	    	String producto= "";
	    	int i = in;
	    		producto= products[i][0]+";"+products[i][1]+";"+products[i][2]+";";
	        return producto;
	    }
	    
	    public static String quantityProductsSelled(int idVend, int quanProduc) {
	    	
	    	int i = idVend;
	    	int p = quanProduc;
	    	String productSelled = "";
	    	String idSeller= "";
	    	String idProducto = "";
	    	idSeller = idEmpleado(i);
    		idProducto= products[p][0]+";";
    		Random random = new Random();
    		int cantVenta = random.nextInt(20);
    		productSelled = idSeller + idProducto + ";"+ cantVenta + ";";
	    	return productSelled;
	    };
	    
	    
	    public static String idEmpleado (int cantEmpleados) {
	    	String idSeller = "";
	    	int i = cantEmpleados;
	    	idSeller = numIdEmpleado[i][0]+ ";" + numIdEmpleado[i][1]+ ";" ;
	    	return idSeller; 
	    }
	    
	       /*
		    public static String generateRandomName() {
		        Random random = new Random();
		        String prefix = name[random.nextInt(name.length)];
		        String suffix = lastName[random.nextInt(lastName.length)];
		        return prefix + " ; " + suffix;
		    }*/
}