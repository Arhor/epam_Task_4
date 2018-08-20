package by.epam.task4.model;

public class Vitamin extends Medicine {

	private Solution solution;
	
	public static enum Solution {
		WATER, FAT
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}
}
