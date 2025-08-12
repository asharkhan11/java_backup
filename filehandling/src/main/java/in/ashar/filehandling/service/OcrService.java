package in.ashar.filehandling.service;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class OcrService {

    public String getTextFromImage(MultipartFile file){
        String data = "";


//        try{
//            BufferedImage image = ImageIO.read(file.getInputStream());
//            if(image == null) return "Invalid Image";
//
//            Tesseract tesseract = new Tesseract();
//            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
//            tesseract.setLanguage("eng");
//            tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY);
//            data = tesseract.doOCR(image);
//
//        } catch (IOException | TesseractException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        return data;


        Path path = Paths.get("C:\\Users\\jalas\\Downloads\\ocr.png");

        try {
             Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return e.getMessage();
        }

        Tesseract tesseract = new Tesseract();

        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
        tesseract.setOcrEngineMode(1);
        tesseract.setLanguage("eng");

        try {
            data = tesseract.doOCR(path.toFile());
        } catch (TesseractException e) {
            return e.getMessage();
        }

        return data;
    }
}
