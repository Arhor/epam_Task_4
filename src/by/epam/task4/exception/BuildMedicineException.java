package by.epam.task4.exception;

public class BuildMedicineException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6856437532042142635L;
    
    public BuildMedicineException() {
        super();
    }
    
    public BuildMedicineException(String message) {
        super(message);
    }
    
    public BuildMedicineException(String message, Exception e) {
        super(message, e);
    }
}
