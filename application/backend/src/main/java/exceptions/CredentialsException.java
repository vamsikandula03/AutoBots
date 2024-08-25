package exceptions;

public class CredentialsException extends Exception{
    public CredentialsException() {
    }

    public CredentialsException(String message) {
        super(message);
    }
}
