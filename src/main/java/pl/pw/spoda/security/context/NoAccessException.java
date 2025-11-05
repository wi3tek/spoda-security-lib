package pl.pw.spoda.security.context;

public class NoAccessException extends Exception {

    public NoAccessException(String message) {
        super( message );
    }
}
