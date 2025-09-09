package in.ashar.java_mail_app.exception;

public class ErrorWhileStoringFile extends RuntimeException {
    public ErrorWhileStoringFile(String message) {
        super(message);
    }
}
