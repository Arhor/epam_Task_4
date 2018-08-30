package by.epam.task4.service.factory;

import by.epam.task4.exception.MedicineNotPresentedException;
import by.epam.task4.model.Analgetic;
import by.epam.task4.model.Antibiotic;
import by.epam.task4.model.Medicine;
import by.epam.task4.model.Vitamin;
import by.epam.task4.service.parsing.ElementsEnum;

public class MedicineFactory {

	public Medicine getMedicine(ElementsEnum element)
			throws MedicineNotPresentedException {
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
				throw new MedicineNotPresentedException();
		}
		return medicine;
	}
}
