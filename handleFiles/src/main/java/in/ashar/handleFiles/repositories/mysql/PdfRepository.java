package in.ashar.handleFiles.repositories.mysql;

import in.ashar.handleFiles.entities.mysql.PdfTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends JpaRepository<PdfTable,Integer> {
}
