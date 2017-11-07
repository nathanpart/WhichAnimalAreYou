package partridge.nathan.whichanimalareyou.model;


public class CharacterResourceException extends Exception {
    public CharacterResourceException() {
        super("Problem in reading character.xml");
    }

    public CharacterResourceException(String message) {
        super(message);
    }

    public CharacterResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharacterResourceException(Throwable cause) {
        super(cause);
    }

}
