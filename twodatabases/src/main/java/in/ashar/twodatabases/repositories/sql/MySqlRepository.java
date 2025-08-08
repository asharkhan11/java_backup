package in.ashar.twodatabases.repositories.sql;

import in.ashar.twodatabases.entities.sql.SqlDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySqlRepository extends JpaRepository<SqlDemo,Integer> {
}
