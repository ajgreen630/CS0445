package edu.pitt.cs.as4;

/**
 * A class that represents a product in a stock management system
 * @author Jon Rutkauskas
 * @author Brian Nixon
 * @version 1.0
 */
public class Product{
	private String itemName;
	private int quantityInStock;
	private double price;
	
	public Product(String itemName, int quantity, double pricePerEach) {
		this.itemName = itemName;
		this.quantityInStock = quantity;
		this.price = pricePerEach;
	}
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String newName) {
		itemName = newName;
	}

	public int getQuantityInStock(){
		return quantityInStock;
	}

	public void setQuantityInStock(int newQuantity) {
		quantityInStock = newQuantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

	public String toString() {
		return quantityInStock + " " + itemName;
	}
}