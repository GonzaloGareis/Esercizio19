package esercizio19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForexReader {

	private static final Map<String, Map<String, Double>> forexMap = new HashMap<>();


	public static void readAndPopulateForexMap(String filePath) {
		ExecutorService executorService = Executors.newFixedThreadPool(4);

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] lineParts = line.split(",");

				String currency = lineParts[0].trim();
				String pillar = lineParts[1].trim();
				double value = Double.valueOf(lineParts[2].trim());

				executorService.submit(() -> populateForexMap(currency, pillar, value));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private static void populateForexMap(String currency, String pillar, double value) {
		forexMap.computeIfAbsent(currency, k -> new HashMap<>()).put(pillar, value);
	}


	public static void printForexCurve(String currency) {
		Map<String, Double> pillarMap = forexMap.get(currency);
		if (pillarMap != null) {
			pillarMap.forEach((pillar, value) -> System.out.println(pillar + " : " + value));
		} else {
			System.out.println("Currency not found");
		}
	}
}