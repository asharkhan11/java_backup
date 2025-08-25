package in.ashar.handleFiles.entities.mysql;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int invoiceNo;
    private int customerNo;
    private String invoicePeriod;
    private String date;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PdfTable> pdfTable;
}
