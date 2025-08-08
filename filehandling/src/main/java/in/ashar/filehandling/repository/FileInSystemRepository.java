package in.ashar.filehandling.repository;

import in.ashar.filehandling.entity.FileInSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInSystemRepository extends JpaRepository<FileInSystem,Integer> {
}
