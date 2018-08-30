package by.epam.task4.exception;

public class MedicineNotPresentedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -1364027759215712943L;

    public MedicineNotPresentedException() {
        super();
    }
    
    public MedicineNotPresentedException(String message) {
        super(message);
    }
}
