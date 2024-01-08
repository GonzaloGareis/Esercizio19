package esercizio19;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		String filePath = "C:\\Users\\HGareis\\Documents\\Currencies CSV.txt";

		ForexReader.readAndPopulateForexMap(filePath);

		System.out.println("Insert the currency: ");

		Scanner scanner = new Scanner(System.in);
		String currency = scanner.next();
		scanner.close();

		ForexReader.printForexCurve(currency);
	}
}