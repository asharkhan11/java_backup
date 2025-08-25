package in.ashar.scanCheque.utility;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartInputResource extends InputStreamResource {

    private final String filename;

    public MultipartInputResource(MultipartFile multipartFile) throws IOException {
        super(multipartFile.getInputStream());
        this.filename = multipartFile.getOriginalFilename();
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() {
        return -1; // we don’t know the length in advance
    }
}
