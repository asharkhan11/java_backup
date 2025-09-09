package in.ashar.java_mail_app.dto;

import jakarta.mail.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentAndFiles {

    private StringBuilder content = new StringBuilder();
    private StringBuilder html = new StringBuilder();
    private List<String> files = new ArrayList<>();

    private transient Message originalMessage;
}
