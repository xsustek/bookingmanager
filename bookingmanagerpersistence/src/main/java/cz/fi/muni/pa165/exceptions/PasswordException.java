package cz.fi.muni.pa165.exceptions;

/**
 * Password exception if encoding or decoding, thrown if there were problem during usage
 *
 * @author Tomas Kopecky
 */
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
