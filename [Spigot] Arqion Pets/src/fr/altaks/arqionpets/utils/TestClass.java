package fr.altaks.arqionpets.utils;

public class TestClass {
	
	public static void main(String[] args) {
		
		int offset = 10;
		for(int line = 0; line < 3; line++) {
			for(int row = 0; row < 3; row++) {
				System.out.print(line * 9 + row + offset + " ");
			}
			System.out.print("\n");
		}
		
	}

}
