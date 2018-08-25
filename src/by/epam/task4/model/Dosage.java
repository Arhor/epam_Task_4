package by.epam.task4.model;

public class Dosage {

	private String amount;
	private String frequency;
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += amount == null ? 0 : amount.hashCode();
		hashCode += frequency == null ? 0 : frequency.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "\n        " + getClass().getSimpleName() + ":"
				+ "\n            amount:    " + amount
				+ "\n            frequency: " + frequency;
	}
}
