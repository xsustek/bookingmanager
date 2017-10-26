package cz.fi.muni.pa165.exceptions;

public class PasswordException extends Throwable {
    public PasswordException() {
        super();
    }

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
