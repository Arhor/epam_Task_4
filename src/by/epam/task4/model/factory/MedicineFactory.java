package by.epam.task4.model.factory;

import by.epam.task4.model.Analgetic;
import by.epam.task4.model.Antibiotic;
import by.epam.task4.model.Medicine;
import by.epam.task4.model.Vitamin;
import by.epam.task4.service.sax.ElementsEnum;

public class MedicineFactory {

	public Medicine getMedicine(ElementsEnum element) {
		Medicine medicine = null;
		switch (element) {
			case ANTIBIOTIC:
				medicine = new Antibiotic();
				break;
			case VITAMIN:
				medicine = new Vitamin();
				break;
			case ANALGETIC:
				medicine = new Analgetic();
				break;
			default:
				break;
		}
		return medicine;
	}
}
