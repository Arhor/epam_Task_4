package by.epam.task4.service.parsing;

import java.util.Set;

import by.epam.task4.model.Medicine;

public abstract class MedicinsAbstractBuilder {

	protected Set<Medicine> medicins;
	
	public abstract void buildSetMedicins(String xml);

	public Set<Medicine> getMedicins() {
		return medicins;
	}

}
