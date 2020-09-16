package edu.pitt.cs.as4;

/**
 * A class that represents a category of products in a stock management system
 * @author Jon Rutkauskas
 * @author Brian Nixon
 * @version 1.0
 */
public class Category{
	private ListInterface<Product> products;
	private String categoryName;


	public Category(String categoryName)	// DONE?
	{	
		this.categoryName = categoryName;
		products = new ArrayList<Product>();
	}

	// Returns the name of this category:
	public String getCategoryName()			// DONE
	{		
		return categoryName;
	}

	// Adds a single product to this category:
	public void addProduct(Product prod)	// DONE
	{
		products.add(prod);
	}

	// Returns a product entry given a string of the product's name:
	public Product findProductByName(String productName) 	// DONE
	{
		int numberOfEntries = products.getSize();
		for (int i = 0; i < numberOfEntries; i++)
		{
			Product aProduct = products.get(i);
			if (aProduct.getItemName().equals(productName))
			{
				return aProduct;
			}
		}
		return null; 	// throw exception?
	}

	// Removes a product entry from this category and returns it:
	public Product removeProductByName(String productName) 
	{
		// TODO: Throw exception if product is not found...
		int numberOfEntries = products.getSize();
		for (int i = 0; i < numberOfEntries; i++)
		{
			Product aProduct = products.get(i);
			if (aProduct.getItemName().equals(productName))
			{
				return products.remove(i);
			}
		}
		return null; // or throw exception?
	}

	// Returns the number of products in this category:
	public int getSize() 			// DONE?
	{
		return products.getSize();
	}

	// Returns the total number of items stocked in this category (sum of all quantities):
	public int getTotalQuantityOfStock()			// DONE?
	{
		int numberOfEntries = products.getSize();
		int totalQuantity = 0;
		for (int i = 0; i < numberOfEntries; i++)
		{
			Product aProduct = products.get(i);
			totalQuantity += aProduct.getQuantityInStock();
		}
		return totalQuantity;
	}

	// Returns the value of all products in the system: Sum(Price * Quantity) for each 
	// Product in this category:
	public double getTotalValue()					// DONE
	{
		int numberOfEntries = products.getSize();
		double totalValue = 0;
		for (int i = 0; i < numberOfEntries; i++)
		{
			Product aProduct = products.get(i);
			double productValue = aProduct.getPrice() * aProduct.getQuantityInStock();
			totalValue += productValue;
		}
		return totalValue;
	}

	// Returns a new List containing all products in this category.  Do not directly return 
	// the private backing List:
	public ListInterface<Product> getAllProducts() 
	{
		// TODO: Complete this method...
		// Create new list of the same size as as products.
		ListInterface<Product> productList = new ArrayList<Product>(products.getSize());
		
		int numProductEntries = products.getSize();		// Number of entries in products.
		for (int i = 0; i < numProductEntries; i++)	// Add each entry in products to productList.
		{
			Product aProduct = products.get(i);			// Get an entry in products at index i.
			productList.add(aProduct);					// Add aProduct to productList.
		}
		return productList;
	}

	
}