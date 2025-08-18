package in.ashar.filehandling.service;

import in.ashar.filehandling.utility.SinglePincodeReader;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OcrService {

    public String getTextFromImage(MultipartFile file) {
        String data = "";
//
//
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


        Path path = Paths.get("C:\\Users\\jalas\\Downloads\\ocr.pdf");

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return e.getMessage();
        }

        Tesseract tesseract = new Tesseract();


        tesseract.setPageSegMode(3);
        tesseract.setOcrEngineMode(3);
        tesseract.setLanguage("eng");
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");



        try {
            data = tesseract.doOCR(path.toFile());

        } catch (TesseractException e) {
            return e.getMessage();
        }

//        return  extractData(data);

        SinglePincodeReader reader = new SinglePincodeReader();
        try {
            reader.findPlace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    private String extractData(String data) {

        String gender = "";
        String dob = "";
        String aadhaar = "";

        // Step 1: Clean OCR noise
        String clean = data.replaceAll("[\\r\\t]+", " ")
                .replaceAll("\\n+", "\n")
                .replaceAll("\\s{2,}", " ")
//                .replaceAll("\\b[A-Z]{2,}\\b", "") // remove uppercase garbage
                .replaceAll("[^\\p{L}\\p{N}\\s:/,.-]", "") // remove stray symbols
                .trim();

// gender

        if(clean.contains("MALE") || clean.contains("Male")){
            gender = "Male";
        }
        if(clean.contains("FEMALE") || clean.contains("Female")){
            gender = "Female";
        }

//DOB
        if(clean.contains("DOB")){
            int i = clean.indexOf("DOB");
            dob = clean.substring(i+5, i+15);
        }

// aadhaar ID

        Pattern aadhaarPattern = Pattern.compile("\\b\\d{4}\\s+\\d{4}\\s+\\d{4}\\b");
        Matcher mAadhaar = aadhaarPattern.matcher(clean);

        if (mAadhaar.find()) {
            aadhaar = mAadhaar.group();
        }

        System.out.println("gender : " +gender);
        System.out.println("date of birth : " +dob);
        System.out.println("Aadhaar ID : " +aadhaar);
        return clean;
    }
}
