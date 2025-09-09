package in.ashar.java_mail_app.exception;

public class ErrorWhileSendingMail extends RuntimeException {
    public ErrorWhileSendingMail(String message) {
        super(message);
    }
}
