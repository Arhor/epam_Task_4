package by.epam.task4.model;

public class Antibiotic extends Medicine {

	private boolean recipe;

	public boolean isRecipe() {
		return recipe;
	}

	public void setRecipe(boolean recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += super.hashCode();
		hashCode += recipe ? 1 : 0;
		return hashCode;
	}
	
	@Override
	public String toString() {
		return super.toString()
				+ ", recipe: " + recipe; 
	}
}
