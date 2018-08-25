package by.epam.task4.model;

public class Analgetic extends Medicine {

	private boolean narcotic;

	public boolean isNarcotic() {
		return narcotic;
	}

	public void setNarcotic(boolean narcotic) {
		this.narcotic = narcotic;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += super.hashCode();
		hashCode += narcotic ? 1 : 0;
		return hashCode;
	}
	
	@Override
	public String toString() {
		return super.toString()
				+ ", narcotic: " + narcotic;
	}
}
