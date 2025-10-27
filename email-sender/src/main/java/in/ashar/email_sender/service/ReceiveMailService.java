package in.ashar.email_sender.service;

import in.ashar.email_sender.dto.MessageResponse;
import in.ashar.email_sender.utility.ImapConfigurationProperties;
import in.ashar.email_sender.utility.MailFolders;
import jakarta.annotation.PreDestroy;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ReceiveMailService {

    @Autowired
    private ImapConfigurationProperties imapConfigurationProperties;
    private Store store;

    @PreDestroy
    public void preDestroy() throws MessagingException {
        store.close();
    }

    public List<MessageResponse> readRecentMessages(int count, MailFolders mailFolder) throws MessagingException, IOException {
        Folder inbox = getMailFolder(mailFolder);
        inbox.open(Folder.READ_ONLY);
        int messageCount = inbox.getMessageCount();
        int startIndex = Math.max(1, messageCount - count + 1);

        Message[] messages = inbox.getMessages(startIndex, messageCount);

        return getResponse(messages);

    }

    public List<MessageResponse> readRecentUnseenMessages(int count, MailFolders mailFolder) throws MessagingException, IOException {
        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        FlagTerm unseen = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

        Message[] messages = folder.search(unseen);

        int totalMessages = messages.length;
        int startIndex = Math.max(0, totalMessages - count);
        Message[] messages1 = Arrays.copyOfRange(messages, startIndex, totalMessages);
        return getResponse(messages1);
    }

    public List<MessageResponse> readMessagesByDate(MailFolders mailFolder, Date date) throws MessagingException, IOException {

        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        ReceivedDateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.EQ, date);

        Message[] messages = folder.search(dateTerm);

        return getResponse(messages);
    }

    public List<MessageResponse> readMessagesByReceivedFrom(MailFolders mailFolder, String email) throws MessagingException, IOException {
        Folder folder = getMailFolder(mailFolder);
        folder.open(Folder.READ_ONLY);

        FromStringTerm fromStringTerm = new FromStringTerm(email);

        Message[] messages = folder.search(fromStringTerm);
        System.out.println(messages.length);

        return getResponse(messages);

    }

    /// /////////////// HELPER Methods ///////////////////////////////

    private void connectToIMAP() throws MessagingException {
        if (store != null) return;

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

        String folderToAccess = switch (folder) {
            case ALL -> "[Gmail]/All Mail";
            case INBOX -> "INBOX";
            case SENT -> "[Gmail]/Sent Mail";
            case DRAFTS -> "[Gmail]/Drafts";
            case SPAM -> "[Gmail]/Spam";
            case TRASH -> "[Gmail]/Trash";
        };

        return store.getFolder(folderToAccess);
    }

    private List<MessageResponse> getResponse(Message[] messages){
        List<MessageResponse> responses = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (Message message : messages) {
            MimeMessage message1 = (MimeMessage) message;
            executorService.submit(() -> {
                try {
                    MessageResponse response = handleResponse(message1);
                    responses.add(response);
                } catch (MessagingException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.close();
        }

        return responses;
    }

    private MessageResponse handleResponse(MimeMessage message) throws MessagingException, IOException {
        MessageResponse response = new MessageResponse();
        response.setReceivedFrom(Arrays.stream(message.getFrom()).map((address -> (InternetAddress) address)).map(InternetAddress::getAddress).toList());
        response.setSentTo(Arrays.stream(message.getAllRecipients()).map(address -> (InternetAddress) address).map(InternetAddress::getAddress).toList());
        response.setReceivedOn(message.getReceivedDate().toString());
        response.setSubject(message.getSubject());
        response.setSeen(message.getFlags().contains(Flags.Flag.SEEN));

        String messageType = message.getContentType();
        if (messageType.contains("TEXT")) {
            response.setContent(message.getContent().toString());
        } else if (messageType.contains("multipart")) {
            Multipart body = (Multipart) message.getContent();
            StringBuilder content = new StringBuilder();
            List<String> files = new ArrayList<>();

            for (int i = 0; i < body.getCount(); i++) {
                BodyPart part = body.getBodyPart(i);
                if (part.getContentType().contains("TEXT")) {
                    content.append(part.getContent()).append(System.lineSeparator());
                } else {
                    files.add(part.getFileName());
                }
            }
            response.setContent(content.toString());
            response.setFiles(files);

        }
        return response;
    }


}
