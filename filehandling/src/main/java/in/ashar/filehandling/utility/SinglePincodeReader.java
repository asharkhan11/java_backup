package in.ashar.filehandling.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.filehandling.entity.Place;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SinglePincodeReader {
    public void findPlace() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Read JSON array into a list of Place objects
        List<Place> places = mapper.readValue(
                new File("C:\\Users\\jalas\\Downloads\\all-india-pincode-json-array.json"),
                new TypeReference<List<Place>>() {}
        );

        String searchPin = "413103"; // This could come from OCR output

        // Filter by matching pincode
        List<Place> matches = places.stream()
                .filter(p -> searchPin.equals(String.valueOf(p.pincode)))
                .toList();

        // Output all matches
        if (matches.isEmpty()) {
            System.out.println("No match found for PIN: " + searchPin);
        } else {
            System.out.println("Found " + matches.size() + " matches for PIN " + searchPin + ":");
            for (Place p : matches) {
                System.out.println("Office: " + p.officename);
                System.out.println("Taluk: " + p.Taluk);
                System.out.println("District: " + p.Districtname);
                System.out.println("State: " + p.statename);
                System.out.println("----");
            }

        }
    }
}