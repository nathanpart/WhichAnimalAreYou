package partridge.nathan.whichanimalareyou.model;


public class UnAnsweredQuestionException extends Exception {
    public UnAnsweredQuestionException() {
        super("There is questions with out an answer selected.");
    }

    public UnAnsweredQuestionException(String message) {
        super(message);
    }

    public UnAnsweredQuestionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAnsweredQuestionException(Throwable cause) {
        super(cause);
    }
}
