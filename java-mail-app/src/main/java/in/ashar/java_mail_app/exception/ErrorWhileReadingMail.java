package in.ashar.java_mail_app.exception;

public class ErrorWhileReadingMail extends RuntimeException {
    public ErrorWhileReadingMail(String message) {
        super(message);
    }
}
