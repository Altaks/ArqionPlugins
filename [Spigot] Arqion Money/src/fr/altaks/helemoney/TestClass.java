package fr.altaks.helemoney;

import java.text.NumberFormat;
import java.util.Locale;

public class TestClass {

	public static void main(String[] args) {

		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("fr","FR"));
		
		for(char c : format.format(200.0).toCharArray()) {
			
			System.out.println("ac");
			
		}
		

	}

}
