//package in.ashar.scanCheque.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//
//@Service
//public class OcrService {
//
//    private static final Logger log = LoggerFactory.getLogger(OcrService.class);
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    private static final String OCR_API_URL = "http://localhost:8000/ocr";
//
//    public String extractText(MultipartFile file) throws IOException {
//        // Save MultipartFile temporarily
//        File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
//        file.transferTo(tempFile);
//        log.info("Saved temp file: {}, size={} bytes", tempFile.getAbsolutePath(), tempFile.length());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", new FileSystemResource(tempFile));
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        log.info("Sending file to OCR server at {}", OCR_API_URL);
//
//        ResponseEntity<Map> response =
//                restTemplate.exchange(OCR_API_URL, HttpMethod.POST, requestEntity, Map.class);
//
//        tempFile.delete(); // cleanup
//        log.info("OCR server responded: {}", response.getBody());
//
//        if (response.getBody() != null && response.getBody().containsKey("text")) {
//            return (String) response.getBody().get("text");
//        }
//        return "No text detected";
//    }
//}


package in.ashar.scanCheque.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
public class OcrService {

    private static final Logger log = LoggerFactory.getLogger(OcrService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    // Hugging Face TrOCR handwritten model endpoint
    private static final String HF_MODEL = "microsoft/trocr-base-handwritten";
    private static final String HF_API_URL = "https://api-inference.huggingface.co/models/" + HF_MODEL;

    // Read token from environment variable HF_TOKEN by default
    @Value("HF_TOKEN")
    private final String hfToken;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OcrService() {
        this.hfToken = Optional.ofNullable(System.getenv("HF_TOKEN")).orElse("");
        if (this.hfToken.isEmpty()) {
            log.warn("Hugging Face token (HF_TOKEN) not set. Set HF_TOKEN env var before calling the API.");
        }
    }

    /**
     * Sends the provided MultipartFile to Hugging Face Inference API (TrOCR handwritten).
     *
     * @param file scanned cheque image
     * @return recognized text or explanatory message
     * @throws IOException on file IO errors
     */
    public String extractText(MultipartFile file) throws IOException {
        if (hfToken.isEmpty()) {
            throw new IllegalStateException("HF_TOKEN environment variable is missing. Set HF_TOKEN with your Hugging Face token.");
        }

        // Save MultipartFile temporarily
        File tempFile = File.createTempFile("upload-", "-" + file.getOriginalFilename());
        try {
            file.transferTo(tempFile);
            log.info("Saved temp file: {}, size={} bytes", tempFile.getAbsolutePath(), tempFile.length());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(hfToken);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(tempFile));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            log.info("Sending file to Hugging Face model {} at {}", HF_MODEL, HF_API_URL);

            ResponseEntity<String> response = restTemplate.exchange(
                    HF_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Hugging Face response status: {}", response.getStatusCode());

            String responseBody = response.getBody();
            log.debug("Raw HF response body: {}", responseBody);

            if (responseBody == null || responseBody.trim().isEmpty()) {
                return "No text detected (empty response)";
            }

            // Try parsing the response (HF may return JSON or plain text)
            try {
                JsonNode root = objectMapper.readTree(responseBody);

                // If HF responds with an array, take first element
                if (root.isArray() && root.size() > 0) {
                    root = root.get(0);
                }

                // Common fields to check
                if (root.has("generated_text")) {
                    return root.get("generated_text").asText();
                }
                if (root.has("text")) {
                    return root.get("text").asText();
                }

                // If the JSON root is a string node
                if (root.isTextual()) {
                    return root.asText();
                }

                // Fallback: return the raw JSON string
                return responseBody;

            } catch (Exception e) {
                // Not JSON — maybe plain text
                log.debug("Response is not JSON, returning raw body. Parse error: {}", e.getMessage());
                return responseBody;
            }

        } finally {
            // cleanup
            if (tempFile.exists()) {
                boolean deleted = tempFile.delete();
                log.debug("Deleted temp file {}: {}", tempFile.getAbsolutePath(), deleted);
            }
        }
    }
}
