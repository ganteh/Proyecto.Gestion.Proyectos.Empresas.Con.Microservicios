package com.example.student.Controller;

import com.example.student.Entities.Student;
import com.example.student.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Obtiene todos los estudiantes
     * @return Lista de estudiantes
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    /**
     * Obtiene un estudiante por su ID
     * @param id ID del estudiante
     * @return Estudiante encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene estudiantes por proyecto
     * @param projectId ID del proyecto
     * @return Lista de estudiantes asociados al proyecto
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Student>> getStudentsByProject(@PathVariable String projectId) {
        List<Student> students = studentRepository.findByProjectId(projectId);
        return ResponseEntity.ok(students);
    }

    /**
     * Crea un nuevo estudiante
     * @param student Datos del estudiante
     * @return Estudiante creado
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    /**
     * Actualiza un estudiante existente
     * @param id ID del estudiante
     * @param student Datos actualizados
     * @return Estudiante actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        student.setId(id);
        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * Elimina un estudiante
     * @param id ID del estudiante
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Asigna un proyecto a un estudiante
     * @param id ID del estudiante
     * @param projectId ID del proyecto
     * @return Estudiante actualizado
     */
    @PatchMapping("/{id}/project/{projectId}")
    public ResponseEntity<Student> assignProjectToStudent(@PathVariable int id, @PathVariable String projectId) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setProjectId(projectId);
                    return ResponseEntity.ok(studentRepository.save(student));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}