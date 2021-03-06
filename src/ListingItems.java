import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ListingItems {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		double total = 0;
		Map<String, Integer> allItems = new HashMap<>();
		List<String> items = new ArrayList<>();
		String[] l = {"Latte", "Croissant", "Breakfast bagel", "Mystery coffee", "Donut",
		 "Bottled Water", "Raisins", "Tea"};
		for (int i = 0; i < l.length; i++) {
			items.add(l[i]);
		}
		
		List<Double> prices = new ArrayList<>();
	Double[] p = {1.99, 3.25, 6.42, 5.66, 6.20,
				 5.21, 8.01, 10.00};
				for (int i = 0; i < p.length; i++) {
					prices.add(p[i]);
				}
		getUserCommand(sc, items, prices, total, allItems);
		
		}

	private static void getUserCommand(Scanner sc, List<String>items, List<Double> prices, double total, Map<String, Integer> allItems) {
		System.out.println("Type the number of one of the following commands: 1. view menu, 2. add item to cart,  3. checkout");
		String answer = sc.nextLine();
		if (answer.matches("1")) {
			displayItemsAndPrices(sc, items, prices, total, allItems);
		} else if (answer.matches("2")) {
			getUserInputPurchase(sc, items, prices, total, allItems);
		} else if (answer.matches("3")) {
			checkout(sc, items, total, allItems, prices);
		} else {
			System.out.println("Try again");
			getUserCommand(sc, items, prices, total, allItems);
		}
	}

	private static void getUserInputPurchase(Scanner sc, List<String> items, List<Double> prices, double total, Map<String, Integer> allItems) {
		System.out.println("What would you like to buy? Enter the item or the item number:");
		String answer = sc.nextLine();
		int itemIndex = 0;
		String item = "";
		try {
		if (items.contains(answer)) {
			item = answer;
			itemIndex = items.indexOf(item);
		} else if (items.contains(items.get(Integer.parseInt(answer) - 1))) {
			item = items.get(Integer.parseInt(answer) - 1);
			itemIndex = Integer.parseInt(answer) - 1;
		} 
		}
		catch (IndexOutOfBoundsException | NumberFormatException ex) {
			System.out.println("I think you goofed");
			getUserInputPurchase(sc, items, prices, total, allItems);
		}
		System.out.print(item + " costs $");
		System.out.printf("%.2f", prices.get(itemIndex));
		System.out.println("");
		total = calcTotal(sc, items, prices, item, total, itemIndex);
		allItems = keepTrackOfItems(sc, items, prices, item, allItems);
		System.out.print("Your total is $");
		System.out.printf("%.2f", total);
		System.out.println(".");
		System.out.print("Your cart contains: ");
		printCartContents(sc, items, prices, total, allItems);
		System.out.println("What would you like to do next?");
		getUserCommand(sc, items, prices, total, allItems);
	}

	private static void printCartContents(Scanner sc, List<String> items, List<Double> prices, double total, Map<String, Integer> allItems) {
		// TODO Auto-generated method stub
		for (String key : allItems.keySet()) {
			System.out.print(allItems.get(key)+ " "); 
			System.out.print(key + " ");
		}
		System.out.println("");
	}

	private static double calcTotal(Scanner sc, List<String> items, List<Double> prices, String item, double total, int itemIndex) {
		double price = prices.get(itemIndex);
		total += price;
		return total;
	}
	
	private static Map<String, Integer> keepTrackOfItems(Scanner sc, List<String> items, List<Double> prices, String item, Map<String, Integer> allItems) {
		if (allItems.containsKey(item) == false) {
		allItems.put(item, 1);
		} else {
			allItems.put(item, allItems.get(item) + 1);
		}
		return allItems;
	}

	private static void checkout(Scanner sc, List<String> items, double total, Map<String, Integer> allItems, List<Double> prices) {
		// TODO Auto-generated method stub
		System.out.println("Thanks for your order! Here's your receipt.");
		System.out.printf("%-20s", "Item"); System.out.printf("%-20s", "Amount"); System.out.printf("%-20s", "PricePerUnit"); System.out.printf("%-20s", "TotalPerItem");
		System.out.println("");
		for (Object key : allItems.keySet()) {
			int pricePerUnitIndex = items.indexOf(key);
			double pricePerUnit = prices.get(pricePerUnitIndex);
			
					
		    System.out.printf("%-20s", key);
		    Integer value = allItems.get(key);
		    System.out.printf("%-20s", value);
		    System.out.printf("%.2f", pricePerUnit);
		    System.out.printf("%-16s", " ");
		    System.out.printf("%.2f", pricePerUnit * value);
		    System.out.printf("%-16s", " ");
		    System.out.println("");
		}
		System.out.print("Your total is: $");
		System.out.printf("%.2f", total);
		System.out.println("");
		System.out.println("most expensive item is " + getMostExpensiveItem(sc, items, total, allItems, prices));
		System.out.println("most expensive item is " + getLeastExpensiveItem(sc, items, total, allItems, prices));
	}

	private static String getLeastExpensiveItem(Scanner sc, List<String> items, double total, Map<String, Integer> allItems, List<Double> prices) {
		String min = "";
		double minPrice = 11.00;
		for (String key : allItems.keySet()) {
		int pricePerUnitIndex = items.indexOf(key);
		double pricePerUnit = prices.get(pricePerUnitIndex);
	    
	    if (pricePerUnit <= minPrice) {
	    	minPrice = pricePerUnit;
	    	min = key;
	    }
	}
	return min;
	}

	private static String getMostExpensiveItem(Scanner sc, List<String> items, double total, Map<String, Integer> allItems, List<Double> prices) {
		String max = "";
		double maxPrice = 0;
		for (String key : allItems.keySet()) {
			int pricePerUnitIndex = items.indexOf(key);
			double pricePerUnit = prices.get(pricePerUnitIndex);
		    
		    if (pricePerUnit>= maxPrice) {
		    	maxPrice = pricePerUnit;
		    	max = key;
		    }
		}
		return max;
	}

	private static void displayItemsAndPrices(Scanner sc, List<String> items, List<Double> prices, double total, Map<String, Integer> allItems) {
		System.out.printf("%-23s", "Items");
		System.out.printf("%-20s", "Price");
		System.out.println("");
		System.out.println("============================");
		for (int i = 0; i < items.size(); i++) {
			System.out.printf(i + 1 + ". " + "%-20s", items.get(i));
			System.out.print("$");
			System.out.printf("%.2f", prices.get(i));
			System.out.println("");
	}
		getUserCommand(sc, items, prices, total, allItems);
	}
}
