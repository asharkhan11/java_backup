package in.ashar.handleFiles.repositories.oracle;

import in.ashar.handleFiles.entities.oracle.ExcelTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends JpaRepository<ExcelTable,Integer> {
}
