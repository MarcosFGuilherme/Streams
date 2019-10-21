package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.Product;
import general.Utility;


public class Program {

	public static void main(String[] args) {
		// showDemoStream();
		// showDemoPipiline();
		showDemoProduct();
	}

	public static void showDemoStream() {
		System.out.println("Demo Stream basic");
		System.out.println("===================");
		List<Integer> list = Arrays.asList(3, 4, 5, 10, 7);
		System.out.println("Show original all elements numbers.");
		Stream<Integer> st1 = list.stream();
		System.out.println(" " + Arrays.toString(st1.toArray()));

		System.out.println("Mutiply all elements for ten.");
		st1 = list.stream().map(x -> x * 10);
		System.out.println(" " + Arrays.toString(st1.toArray()));

		System.out.println("Show original all elements string.");
		Stream<String> st2 = Stream.of("Maria", "Alex", "Bob");
		System.out.println(" " + Arrays.toString(st2.toArray()));

		System.out.println("Show original all elements numbers of iterate.");
		Stream<Integer> st3 = Stream.iterate(0, x -> x + 2);
		System.out.println(" " + Arrays.toString(st3.limit(10).toArray()));

		System.out.println("Show elements numbers of Fibonacci[Fn = Fn-1 + Fn-2].");
		Stream<Long> st4 = Stream.iterate(new Long[] { 0L, 1L }, p -> new Long[] { p[1], p[0] + p[1] }).map(p -> p[0]);
		System.out.println(" " + Arrays.toString(st4.limit(15).toArray()));

		System.out.println();
	}

	public static void showDemoPipiline() {
		System.out.println("Demo Pipiline");
		System.out.println("==============");
		List<Integer> list = Arrays.asList(3, 4, 5, 10, 7);
		System.out.println("Show original all elements numbers.");
		Stream<Integer> st1 = list.stream().map(x -> x * 10);
		System.out.println(" " + Arrays.toString(st1.toArray()));

		System.out.println("Sum of my original list.");
		int sum = list.stream().reduce(0, (x, y) -> x + y);
		System.out.println(" Sum = " + sum);

		System.out.println("New list from original list with new parameters.");
		List<Integer> newList = list.stream().filter(x -> x % 2 == 0).map(x -> x * 10).collect(Collectors.toList());

		System.out.println(" " + Arrays.toString(newList.toArray()));
	}

	public static void showDemoProduct() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter full file path: ");
		String pathIn = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathIn))) {
			
			List<Product> list = new ArrayList<Product>();
			
			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				Product Prod = new Product(fields[0], Double.parseDouble(fields[1]));
				list.add(Prod);
				line = br.readLine();
			}
			
			showProducts(list,"Original List");
			showAverage(list);
			showProducts(list,"Products with less than average price");
			
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		
		
		
		sc.close();
	}
	public static void showProducts(List<Product> list, String title) {
		System.out.println(Utility.stringFix("", 50, "="));
		System.out.println(Utility.stringFix(title, 50, " "));
		System.out.println(Utility.stringFix("", 50, "="));
		System.out.println(Utility.stringFix("Product", 20, " ") + Utility.stringFix("Price", 10, " "));
		System.out.println(Utility.stringFix("", 30, "-"));
		for(Product p : list) {
			System.out.print(Utility.stringFix(p.getName(), 20, " "));
			System.out.println(Utility.stringFix(String.format("%.2f", p.getPrice()), 10, " "));
		}
		System.out.println(Utility.stringFix("", 30, "-"));
		System.out.println(Utility.stringFix("", 50, "="));
	}
	public static void showAverage(List<Product> list) {
		System.out.print("Average price: ");
		double avg = list.stream()
				.map(p -> p.getPrice())
				.reduce(0.0, (x,y) -> x+y) / list.size();
		System.out.println(String.format("%.2f", avg));		
	}
}
