package by.epam.task4.exception;

public class ParserNotPresentedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 3488687349870618790L;

    public ParserNotPresentedException() {
        super();
    }
    
    public ParserNotPresentedException(String message) {
        super(message);
    }
}
