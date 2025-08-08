package in.ashar.twodatabases.repositories.oracle;

import in.ashar.twodatabases.entities.oracle.OracleDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OracleRepository extends JpaRepository<OracleDemo,Integer> {
}
