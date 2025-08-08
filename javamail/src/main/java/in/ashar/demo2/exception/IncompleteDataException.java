package in.ashar.demo2.exception;

public class IncompleteDataException extends RuntimeException {
    public IncompleteDataException(String message) {
        super(message);
    }
}
