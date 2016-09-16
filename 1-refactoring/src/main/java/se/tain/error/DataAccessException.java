package se.tain.error;

public class DataAccessException extends Exception {

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
