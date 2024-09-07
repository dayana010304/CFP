package Proyecto;

import java.util.Random;

public class nameRandom {
	
		private static final String[] name = {
		    "Carlos", "Andres", "Laura", "Andrea", "Raul",
		    "Maria", "Juan", "Sofia", "Pedro", "Valentina",
		    "Diego", "Camila", "Luis", "Daniela", "Jose",
		    "Ana", "Fernando", "Carolina", "Jorge", "Isabella"
	    };

	    private static final String[] lastName = {
	    	    "Garcia", "Rodriguez", "Martinez", "Hernandez", "Lopez",
	    	    "Gonzalez", "Perez", "Sanchez", "Ramirez", "Torres",
	    	    "Flores", "Rivera", "Gomez", "Diaz", "Cruz",
	    	    "Ortiz", "Morales", "Moreno", "Vasquez", "Castro"
	    };
	    
       public static String[][] products = {
                {"ID2000", "Toyota Corolla", "20000"},
                {"ID2001", "Honda Civic", "22000"},
                {"ID2002", "Ford Focus", "21000"},
                {"ID2003", "Chevrolet Malibu", "23000"},
                {"ID2004", "Nissan Sentra", "19500"},
                {"ID2005", "Hyundai Elantra", "18000"},
                {"ID2006", "Volkswagen Jetta", "25000"},
                {"ID2007", "Mazda 3", "24000"},
                {"ID2008", "Subaru Impreza", "22500"},
                {"ID2009", "Kia Forte", "19000"}
            };

	    public static String generateRandomName() {
	        Random random = new Random();
	        String prefix = name[random.nextInt(name.length)];
	        String suffix = lastName[random.nextInt(lastName.length)];
	        return prefix + " ; " + suffix;
	    }
	    
	    public static String generateRandomProducts(int in) {
	    	String producto= "";
	    	int i = in;
	    	//for (int i = 0 ; i<products.length; i++)
	    		producto= products[i][0]+";"+products[i][1]+";"+products[i][2]+";";
	        return producto;
	    }
	    
	    

}
