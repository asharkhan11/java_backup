package in.ashar.java_mail_app.utility;

import lombok.Getter;

@Getter
public enum MailFolders {
    ALL("[Gmail]/All Mail"),
    INBOX("INBOX"),
    SENT("[Gmail]/Sent Mail"),
    DRAFTS("[Gmail]/Drafts"),
    SPAM("[Gmail]/Spam"),
    TRASH("[Gmail]/Trash");

    private final String folderName;

    MailFolders(String folderName) {
        this.folderName = folderName;
    }

}

