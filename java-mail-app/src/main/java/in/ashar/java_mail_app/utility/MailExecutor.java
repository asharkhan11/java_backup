package in.ashar.java_mail_app.utility;

import in.ashar.java_mail_app.dto.ContentAndFiles;
import in.ashar.java_mail_app.dto.MessageResponse;
import in.ashar.java_mail_app.exception.ErrorWhileReadingMail;
import in.ashar.java_mail_app.exception.ErrorWhileStoringFile;
import jakarta.annotation.PreDestroy;
import jakarta.mail.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MailExecutor {

    private final Path uploadPath = Paths.get("C:\\Users\\jalas\\OneDrive\\Desktop\\SpringBoot\\java-mail-app\\files");
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @PreDestroy
    public void onDestroy(){
        executor.shutdown();
    }


    public void processMessage2(Message message, List<MessageResponse> responses) {

        ContentAndFiles contentAndFiles = new ContentAndFiles();

        try {
            Object content = message.getContent();

            if (content instanceof String) {
                contentAndFiles.getContent().append(content);
            } else if (content instanceof Multipart multipart) {

                int count = multipart.getCount();


                for (int i = 0; i < count; i++) {
                    BodyPart part = multipart.getBodyPart(i);
                    extractMultipart(part, contentAndFiles);
                }

            }

            MessageResponse response = new MessageResponse(message.getMessageNumber(), Arrays.toString(message.getFrom()), Arrays.toString(message.getAllRecipients()), message.getSubject(), message.getReceivedDate().toString(), message.getFlags().contains(Flags.Flag.SEEN), contentAndFiles.getContent().toString(), contentAndFiles.getHtml().toString(), contentAndFiles.getFiles());
            responses.add(response);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void extractMultipart(BodyPart part, ContentAndFiles contentAndFiles) throws MessagingException, IOException {


        if (part.isMimeType("text/plain")) {
            contentAndFiles.getContent().append(part.getContent().toString());
        } else if (part.isMimeType("text/html")) {
            contentAndFiles.getHtml().append(part.getContent().toString());
        } else if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {

            contentAndFiles.getFiles().add(part.getFileName());
            // write into file system
            executor.submit(() -> {
                writeFileInSystem(part);
            });
            executor.shutdown();


        } else if (Part.INLINE.equalsIgnoreCase(part.getDisposition()) || part.getHeader("Content-ID") != null) {

            contentAndFiles.getFiles().add(part.getFileName());
            //write into file system
            executor.submit(() -> {
                writeFileInSystem(part);
            });
            executor.shutdown();

        } else if (part.getContent() instanceof Multipart multipart) {
            for (int i = 0; i < multipart.getCount(); i++) {
                extractMultipart(multipart.getBodyPart(i), contentAndFiles);
            }
        }
    }


    public void writeFileInSystem(BodyPart part) {

        try (InputStream inputStream = part.getInputStream();) {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String id = UUID.randomUUID().toString();
            Path filePath = uploadPath.resolve(id + part.getFileName());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new ErrorWhileStoringFile(e.getMessage());
        } catch (MessagingException e) {
            throw new ErrorWhileReadingMail(e.getMessage());
        }
    }

}
