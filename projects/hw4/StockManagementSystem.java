package edu.pitt.cs.as4;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.IOException;

/**
 * A class that represents a stock management system
 * @author Jon Rutkauskas
 * @author Brian Nixon
 * @version 1.0
 */
 public class StockManagementSystem {
	public ListInterface<Category> categories;

	// Constructor.  Instantiates categories with an ArrayList:
	public StockManagementSystem() {		// DONE
		categories = new ArrayList<Category>();
	}

	// Creates a product and adds it to an existing category:
	public void createAndAddProduct(String categoryName, String productName, int quantity, double price) // DONE.
	{
		// TODO: Handle possible garbage categoryName or garbage productName.
		Product aProduct = new Product(productName, quantity, price);
		int numberOfEntries = categories.getSize();
		for (int i = 0; i < numberOfEntries; i++)
		{
			Category aCategory = categories.get(i);
			if(aCategory.getCategoryName().equals(categoryName))
			{
				aCategory.addProduct(aProduct);
			}
		}
	}

	// Returns the number of items (Sum of all quantities) in a category:
	public int getNumberOfStockedItemsInCategory(String categoryName) 		// DONE.
	{
		// TODO: Catch possible error if categoryName is not found.
		int numberOfEntries = categories.getSize();
		for (int i = 0; i < numberOfEntries; i++)
		{
			Category aCategory = categories.get(i);
			if(aCategory.getCategoryName().equals(categoryName))
			{
				return aCategory.getTotalQuantityOfStock();
			}
		}
		return 0;
	}

	// Returns the number of stocked items (sum of all quantities) across multiple categories (given as a list):
	public int getNumberOfStockedItemsInCategories(ListInterface<String> categoryNames) // DONE
	{
		// TODO: Catch possible error is categoryNames list has a garbage entry.
		int numberOfCategoryNames = categoryNames.getSize();
		int numberOfCategories = categories.getSize();
		int numberOfStockedItemsInCategories = 0;
		
		for(int j = 0; j < numberOfCategoryNames; j++)
		{
			Category aCategory = categories.get(j);
			if(categoryNames.contains(aCategory.getCategoryName()))
			{
				numberOfStockedItemsInCategories += aCategory.getTotalQuantityOfStock();
			}
		}
		return numberOfStockedItemsInCategories;
	}

	// Returns the stocked quantity of a specific item:
	public int getQuantityOfItemByName(String productName) 
	{
		// TODO: Handle possible garbage productName.
		
		int quantityOfItemByName = 0;		// Initialize the quatity of items by name.
		int numberOfCategories = categories.getSize();	// Get the size of the categories List.
		for (int i = 0; i < numberOfCategories; i++)	// Iterate through the list of categories.
		{
			Category aCategory = categories.get(i);		// Get an entry from the categories List.
			Product aProduct = aCategory.findProductByName(productName);
			if(aProduct != null)
			{
				quantityOfItemByName += aProduct.getQuantityInStock(); 
			}
		}
		return quantityOfItemByName;
	}

	// Sets the stocked quantity of a specific item:
	public void setQuantityOfItemByName(String productName, int newQuantity)	// DONE
	{
		// TODO: Handle possible garbage productName.
		
		int numberOfCategories = categories.getSize();
		for (int i = 0; i < numberOfCategories; i++)
		{
			Category aCategory = categories.get(i);							// Get an entry from the categories List.
			Product aProduct = aCategory.findProductByName(productName);
			if(aProduct != null)
			{
				aProduct.setQuantityInStock(newQuantity);
			}
		}
	}

	// Removes a product from the system:
	public Product removeProductByName(String productName) 	// DONE
	{ 
		// TODO: Handle possible garbage productName.
		int numberOfCategories = categories.getSize();
		for (int i = 0; i < numberOfCategories; i++)
		{
			Category aCategory = categories.get(i);
			Product aProduct = aCategory.removeProductByName(productName);
			if(aProduct != null)
			{
				return aProduct;
			}
		}
		return null;
	}

	// Removes a category from the system:
	public Category removeCategoryByName(String categoryName) // DONE
	{
		int numberOfCategories = categories.getSize();
		for (int i = 0; i < numberOfCategories; i++)
		{
			Category aCategory = categories.get(i);
			if(aCategory.getCategoryName().equals(categoryName))
			{
				return categories.remove(i);
			}
		}
		return null;
	}

	// Creates and adds new category:
	public void createCategory(String categoryName) 		// DONE
	{
		Category aCategory = new Category(categoryName);
		categories.add(aCategory);
	}

	// Calculates and returns the total value of all items in a given category:
	public double totalValueOfItemsInCategory(String categoryName) {	// Works!
		// TODO: Handle possible garbage categoryName.
		int numberOfCategories = categories.getSize();
		for (int i = 0; i < numberOfCategories; i++)
		{
			Category aCategory = categories.get(i);
			if(aCategory.getCategoryName().equals(categoryName))
			{
				return aCategory.getTotalValue();
			}
		}
		return 0;
	}


	// a scanner to get input from the user
	private static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
		//interactivity

		StockManagementSystem s = new StockManagementSystem();
		preStock(s);
		int selection = -1;

		while (selection != 0) {
			System.out.println("\n=================================");
			System.out.println("Stock Management System Main Menu");
			System.out.println("1. Print stocked items");
			System.out.println("2. Create category");
			System.out.println("3. Create Product");
			System.out.println("4. Delete Product");
			System.out.println("5. Delete Category");
			System.out.println("6. Manage Quantity of Item");
			System.out.println("7. Get amount of items stocked in multiple categories");
			System.out.println("8. Get Total Value of all products in a category");
			System.out.println("0. Quit");
			System.out.print("Selection: ");

			try {
				selection = input.nextInt();
			} catch (NoSuchElementException e) {
				selection = -1;
			} catch (IllegalStateException e) {
				selection = -1;
			}
			input.nextLine();

			switch (selection) {
				case 1:
					print(s);
					break;
				case 2:
					createCat(s);
					break;
				case 3:
					createProd(s);
					break;
				case 4:
					deleteProd(s);
					break;
				case 5:
					deleteCat(s);
					break;
				case 6:
					setQty(s);
					break;
				case 7:
					getQtyAcrossCats(s);
					break;
				case 8:
					getPriceCategory(s);
					break;
				case 0:
					break;
				default:
					// Invalid, just ignore and let loop again
					break;
			}
		}
	}
// interactive functions implemented for students
	private static void createCat(StockManagementSystem s) {
		System.out.print("New Category Name: ");
        String name = input.nextLine();
		s.createCategory(name);

	}


	private static void createProd(StockManagementSystem s) {
		System.out.print("Category to add Product into: ");
        String cat = input.nextLine();
		System.out.print("New Product Name: ");
        String name = input.nextLine();
		System.out.print("Quantity of this item: ");
		int qty = input.nextInt();
		input.nextLine();
		System.out.print("Price of this item: ");
		double price = input.nextDouble();
		input.nextLine();
		s.createAndAddProduct(cat, name, qty, price);

	}

	private static void deleteProd(StockManagementSystem s){
		
		System.out.print("Name of Product to Delete: ");
        String name = input.nextLine();
		Product p = s.removeProductByName(name);
		System.out.println("Removed: " + p);
	}

	private static void deleteCat(StockManagementSystem s){
		System.out.print("Name of Product to Delete: ");
        String name = input.nextLine();
		
		Category c = s.removeCategoryByName(name);
		System.out.println("Removed " + name + " category with " + c.getTotalQuantityOfStock() + " items");
	}


	private static void setQty(StockManagementSystem s) {
		System.out.print("Product Name: ");
        String name = input.nextLine();
		int i = s.getQuantityOfItemByName(name);
		System.out.println("Current quantity of " + name + " is: " + i);
		System.out.print("Enter new quantity of this item (leave blank to leave unchanged): ");
		String str = input.nextLine();
		if(!str.equals("")){
			int qty = Integer.parseInt(str);
			s.setQuantityOfItemByName(name, qty);
		}
		
	}

	private static void getQtyAcrossCats(StockManagementSystem s) {
		ListInterface<String> list = new ArrayList<String>();
		String str = "initialString";
		System.out.println("Enter names of categories, pressing ENTER after each category; Type 'done' to finish: ");
		while(!str.equals("done")) {
			str = input.nextLine();
			if(!str.equals("done"))
				list.add(str);
		}
		int qty = s.getNumberOfStockedItemsInCategories(list);
		System.out.println("Total number of items stocked in those categories is: " + qty);
	}

	private static void print(StockManagementSystem s) {
		System.out.println("=========== All Items ===========");
		for(int i = 0; i < s.categories.getSize(); i++) {
			Category c = s.categories.get(i);
			System.out.println(c.getCategoryName() + " (" + c.getTotalQuantityOfStock() + " items):");
			ListInterface<Product> list = c.getAllProducts();
			for(int j = 0; j < list.getSize(); j++) {
				Product p = list.get(j);
				System.out.println("\t" + (j+1) + ". " + p.getItemName() + " (" + p.getQuantityInStock() + " @ $" + p.getPrice() + " each) ");
			}
		}
	}

	private static void getPriceCategory(StockManagementSystem s) {
		System.out.print("Name of Category: ");
        String name = input.nextLine();
		double d = s.totalValueOfItemsInCategory(name);
		System.out.println("Value of all items in " + name + ": $" + d);
	}

	private static void preStock(StockManagementSystem s) {
		System.out.print("Do you want to pre-stock the system with default values? (y/n): ");
		String entered = input.nextLine();
		if(entered.equals("y")) {
			Category food = new Category("food");
			Product chips = new Product("chips", 13, 1.49);
			Product apple = new Product("apple", 38, 0.79);
			Product soup = new Product("soup", 18, 1.99);
			Product bread = new Product("bread", 20, 1.87);
			food.addProduct(chips);
			food.addProduct(apple);
			food.addProduct(soup);
			food.addProduct(bread);
			s.categories.add(food);

			Category bev = new Category("beverage");
			Product beer = new Product("beer", 36, 5.99);
			Product wine = new Product("wine", 14, 9.99);
			Product vodka = new Product("vodka", 33, 16.50);
			Product oj = new Product("orange juice", 19, 2.49);
			Product coffee = new Product("coffee", 8, 3);

			bev.addProduct(beer);
			bev.addProduct(wine);
			bev.addProduct(vodka);
			bev.addProduct(oj);
			bev.addProduct(coffee);
			s.categories.add(bev);

			Category elec = new Category("electronics");
			elec.addProduct(new Product("mini-dp to hdmi", 2, 13.99));
			elec.addProduct(new Product("cd player", 8, 19.99));
			elec.addProduct(new Product("cell phone", 5, 99));
			elec.addProduct(new Product("tv remote", 6, 13));
			elec.addProduct(new Product("lamp", 19, 10));
			s.categories.add(elec);
		}
	}



}