package partridge.nathan.whichanimalareyou.model;


public class QuestionResourceException extends Exception {
    public QuestionResourceException() {
        super("Problem reading questions.xlm file");
    }

    public QuestionResourceException(String message) {
        super(message);
    }

    public QuestionResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionResourceException(Throwable cause) {
        super(cause);
    }
}
