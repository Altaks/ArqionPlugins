package fr.altaks.heleshop;

import java.text.NumberFormat;
import java.util.Locale;

public class TestClass {
	
	public static void main(String[] args) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.FRANCE);
		System.out.println(format.format(200_000_000.0d));
	}

}
