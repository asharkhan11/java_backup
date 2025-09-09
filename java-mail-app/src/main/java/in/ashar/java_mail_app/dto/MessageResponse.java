package in.ashar.java_mail_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private int messageNumber;
    private String receivedFrom;
    private String sentTo;
    private String subject;
    private String receivedOn;
    private boolean seen;
    private String content;
    private String html;
    private List<String> files;



}
