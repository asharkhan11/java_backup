package in.ashar.java_mail_app.service;

import in.ashar.java_mail_app.dto.MessageResponse;
import in.ashar.java_mail_app.exception.ErrorWhileReadingMail;
import in.ashar.java_mail_app.utility.MailExecutor;
import in.ashar.java_mail_app.utility.ImapConfigurationProperties;
import in.ashar.java_mail_app.utility.MailFolders;
import jakarta.annotation.PreDestroy;
import jakarta.mail.*;
import jakarta.mail.search.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class MailReceiverService {

    private final ImapConfigurationProperties imapConfigurationProperties;
    private Store store;

    private final MailExecutor executor;

    @PreDestroy
    public void preDestroy() throws MessagingException {
        if (store != null && store.isConnected()) {
            store.close();
        }
    }


    public MailReceiverService(ImapConfigurationProperties imapConfigurationProperties, MailExecutor executor) {
        this.imapConfigurationProperties = imapConfigurationProperties;
        this.executor = executor;
    }


    public List<MessageResponse> readRecentMessages(int count, MailFolders mailFolder) throws MessagingException {
        Folder inbox = getMailFolder(mailFolder);
        inbox.open(Folder.READ_ONLY);
        int messageCount = inbox.getMessageCount();

        if (count > messageCount || count < 1) {
            throw new ErrorWhileReadingMail("Invalid message count provided");
        }

        FetchProfile fetchProfile = new FetchProfile();
        fetchProfile.add(FetchProfile.Item.ENVELOPE);
        fetchProfile.add(FetchProfile.Item.FLAGS);


        int startIndex = Math.max(1, messageCount - count + 1);
        Message[] messages = inbox.getMessages(startIndex, messageCount);
        inbox.fetch(messages, fetchProfile);

        return getResponse2(messages);

    }

    public List<MessageResponse> readLastMessages(int count, MailFolders mailFolder) throws MessagingException {
        Folder inbox = getMailFolder(mailFolder);
        inbox.open(Folder.READ_ONLY);
        int messageCount = inbox.getMessageCount();

        if (count > messageCount || count < 1) {
            throw new ErrorWhileReadingMail("Invalid message count provided");
        }

        Message[] messages = inbox.getMessages(1, count);

        return getResponse2(messages);

    }

    public List<MessageResponse> readRecentUnseenMessages(int count, MailFolders mailFolder) throws MessagingException {
        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        FlagTerm unseen = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

        Message[] messages = folder.search(unseen);
        int totalMessages = messages.length;

        if (count > totalMessages || count < 1) {
            throw new ErrorWhileReadingMail("Invalid message count provided");
        }

        int startIndex = Math.max(0, totalMessages - count);
        Message[] messages1 = Arrays.copyOfRange(messages, startIndex, totalMessages);

        return getResponse2(messages1);
    }

    public List<MessageResponse> readLastUnseenMessages(int count, MailFolders mailFolder) throws MessagingException {
        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        FlagTerm unseen = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

        Message[] messages = folder.search(unseen);
        int totalMessages = messages.length;

        if (count > totalMessages || count < 1) {
            throw new ErrorWhileReadingMail("Invalid message count provided");
        }

        Message[] messages1 = Arrays.copyOfRange(messages, 0, count);

        return getResponse2(messages1);
    }

    public List<MessageResponse> readMessagesByDate(MailFolders mailFolder, Date date) throws MessagingException {

        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        ReceivedDateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.EQ, date);

        Message[] messages = folder.search(dateTerm);

        return getResponse2(messages);
    }

    public List<MessageResponse> readMessagesByDate(MailFolders mailFolder, Date startDate, Date endDate) throws MessagingException {

        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        ReceivedDateTerm startDateTerm = new ReceivedDateTerm(ComparisonTerm.GE, startDate);
        ReceivedDateTerm endDateTerm = new ReceivedDateTerm(ComparisonTerm.LE, endDate);

        AndTerm andTerm = new AndTerm(new SearchTerm[]{startDateTerm, endDateTerm});

        Message[] messages = folder.search(andTerm);

        return getResponse2(messages);
    }

    public List<MessageResponse> readMessagesByReceivedFrom(MailFolders mailFolder, String email) throws MessagingException {
        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        FromStringTerm fromStringTerm = new FromStringTerm(email);

        Message[] messages = folder.search(fromStringTerm);

        return getResponse2(messages);

    }


    public List<MessageResponse> readMessagesByRecipient(MailFolders mailFolder, String email) throws MessagingException {
        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        RecipientStringTerm to = new RecipientStringTerm(Message.RecipientType.TO, email);
        RecipientStringTerm cc = new RecipientStringTerm(Message.RecipientType.CC, email);
        RecipientStringTerm bcc = new RecipientStringTerm(Message.RecipientType.BCC, email);

        OrTerm orTerm = new OrTerm(new SearchTerm[]{to, cc, bcc});
        Message[] messages = folder.search(orTerm);

        return getResponse2(messages);

    }


    public List<MessageResponse> readMessagesBySubject(MailFolders mailFolder, String subject) throws MessagingException {
        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        SubjectTerm subjectTerm = new SubjectTerm(subject);

        Message[] messages = folder.search(subjectTerm);

        return getResponse2(messages);

    }

    /// /////////////// HELPER Methods ///////////////////////////////


    private void connectToIMAP() throws MessagingException {
        if (store != null && store.isConnected()) return;

        Properties properties = new Properties();
        properties.put("mail.store.protocol", imapConfigurationProperties.getProtocol());
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", imapConfigurationProperties.getPort());

        Session session = Session.getInstance(properties);
        store = session.getStore();
        store.connect(imapConfigurationProperties.getHost(), imapConfigurationProperties.getUser(), imapConfigurationProperties.getPassword());

    }

    private Folder getMailFolder(MailFolders folder) throws MessagingException {
        if (store == null || !store.isConnected()) {
            connectToIMAP();
        }

        return store.getFolder(folder.getFolderName());
    }



    private List<MessageResponse> getResponse2(Message[] messages) {

        List<MessageResponse> responses;
        try (ExecutorService threads = Executors.newFixedThreadPool(10)) {
            responses = Collections.synchronizedList(new ArrayList<>());

            Arrays.stream(messages).forEach(message -> {
                threads.submit(() -> {
                    executor.processMessage2(message, responses);
                });
            });

            threads.shutdown();

            try {
                threads.awaitTermination(150, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return responses;

    }


}
