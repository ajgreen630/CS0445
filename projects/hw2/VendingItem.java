package edu.pitt.cs.as1;

public class VendingItem implements VendingItemInterface {
	private final double price;
	final String name;
	
	public VendingItem(double price, String name) {
		this.price = price;
		this.name = name;
	}
	 /**
     * Determines the price of the vending item.
     *
     * @return The double of price for this vending item
     */
    public double getPrice() {
		return price;
	}
	 /**
     * Determines the name of the vendingItem.
     *
     * @return The String of name for this vending item
     */
    public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
}