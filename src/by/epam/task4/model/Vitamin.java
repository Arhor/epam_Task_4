package by.epam.task4.model;

public class Vitamin extends Medicine {

	private String solution;

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += super.hashCode();
		hashCode += solution == null ? 0 : solution.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return super.toString()
				+ ", solution: " + solution;
	}
}
