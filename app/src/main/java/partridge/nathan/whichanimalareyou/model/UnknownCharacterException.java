package partridge.nathan.whichanimalareyou.model;


public class UnknownCharacterException extends Exception {
    public UnknownCharacterException(String message) {
        super(message);
    }

    public UnknownCharacterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCharacterException(Throwable cause) {
        super(cause);
    }
}
