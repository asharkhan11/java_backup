package in.ashar.email_sender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private List<String> receivedFrom;
    private List<String > sentTo;
    private String subject;
    private String receivedOn;
    private boolean seen;
    private String content;
    private List<String> files;

}
