package com.anthology.student.controller;

import com.anthology.student.model.Student;
import com.anthology.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Retrieve students from the system based on the provided query parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of students found"),
            @ApiResponse(responseCode = "404", description = "No students found")
    })
    @GetMapping
    public ResponseEntity<?> getStudents(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestHeader(value = "X-Requested-By", required = false) String requestedBy) {
        List<Student> students = studentService.getStudentsByFirstName(firstName);

        if (students.isEmpty()) {
            return ResponseEntity.status(404).body("No students found");
        }
        return ResponseEntity.ok(students);
    }

    @Operation(summary = "Retrieve student details by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(
            @PathVariable Long id,
            @RequestHeader(value = "X-Requested-By", required = false) String requestedBy) {
        Optional<Student> student = studentService.getStudentById(id);

        if (student.isEmpty()) {
            return ResponseEntity.status(404).body("Student not found");
        }
        return ResponseEntity.ok(student.get());
    }
}