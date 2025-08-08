package in.ashar.filehandling.repository;

import in.ashar.filehandling.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileData,Integer> {
}
