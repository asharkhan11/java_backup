package in.ashar.handleFiles.repositories.mysql;

import in.ashar.handleFiles.entities.mysql.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {
    Optional<Invoice> findByInvoiceNo(int id);
}
