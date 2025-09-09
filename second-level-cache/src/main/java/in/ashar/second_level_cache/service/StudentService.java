package in.ashar.second_level_cache.service;

import in.ashar.second_level_cache.entity.Student;
import in.ashar.second_level_cache.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student getById(int id) {
        return studentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student update(int id, Student student) {
        Student existing = studentRepository.findById(id).orElseThrow(RuntimeException::new);
        student.setId(existing.getId());
        return studentRepository.save(student);
    }


    public void delete(int id) {
        studentRepository.deleteById(id);
    }
}
