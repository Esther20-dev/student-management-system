package com.student.service;

import com.student.model.Student;
import com.student.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student addStudent(String firstName, String lastName, String email,
                              int age, String course, double gpa) {
        validateInput(firstName, lastName, email, age, gpa);
        Student student = new Student(0, firstName, lastName, email, age, course, gpa);
        return repository.save(student);
    }

    public Student updateStudent(int id, String firstName, String lastName, String email,
                                 int age, String course, double gpa) {
        Optional<Student> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Student with ID " + id + " not found.");
        }
        validateInput(firstName, lastName, email, age, gpa);
        Student student = new Student(id, firstName, lastName, email, age, course, gpa);
        return repository.save(student);
    }

    public boolean removeStudent(int id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Student with ID " + id + " not found.");
        }
        return repository.deleteById(id);
    }

    public Optional<Student> getStudentById(int id) {
        return repository.findById(id);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public List<Student> getStudentsByCourse(String course) {
        return repository.findByCourse(course);
    }

    public List<Student> searchByName(String name) {
        return repository.findByName(name);
    }

    public List<Student> getTopStudents(int count) {
        return repository.findAll().stream()
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public double getAverageGpa() {
        List<Student> all = repository.findAll();
        if (all.isEmpty()) return 0.0;
        return all.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }

    public int getTotalStudents() {
        return repository.count();
    }

    private void validateInput(String firstName, String lastName, String email, int age, double gpa) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be empty.");
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be empty.");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email address.");
        if (age < 16 || age > 100)
            throw new IllegalArgumentException("Age must be between 16 and 100.");
        if (gpa < 0.0 || gpa > 4.0)
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
    }
}
