package by.epam.task4.model;

public class Pack {

	private int quantity;
	private double price;
	private Size size;
	
	public static enum Size {
		SMALL, MEDIUM, LARGE
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
}
