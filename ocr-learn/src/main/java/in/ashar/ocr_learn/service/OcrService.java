package in.ashar.ocr_learn.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class OcrService {

    public String extractText(MultipartFile file) {

        try {

            BufferedImage read = ImageIO.read(file.getInputStream());

            if (read == null){
                return "invalid image";
            }

            Tesseract model = new Tesseract();

            model.setLanguage("eng");
            model.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");

            String s = model.doOCR(read);

            return s;

        } catch (IOException e) {
            return e.getMessage();
        } catch (TesseractException e) {
            return e.getMessage();
        }

    }
}
