package in.ashar.second_level_cache.controller;

import in.ashar.second_level_cache.entity.Student;
import in.ashar.second_level_cache.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {

        return ResponseEntity.ok(studentService.create(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id) {
        // TODO: implement read by ID
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        // TODO: implement read all
        return ResponseEntity.ok(studentService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student) {
        // TODO: implement update
        return ResponseEntity.ok(studentService.update(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        // TODO: implement delete
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
