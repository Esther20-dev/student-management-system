package com.student.repository;

import com.student.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public Student save(Student student) {
        if (student.getId() == 0) {
            student.setId(nextId++);
            students.add(student);
        } else {
            // Update existing
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == student.getId()) {
                    students.set(i, student);
                    return student;
                }
            }
            students.add(student);
        }
        return student;
    }

    public Optional<Student> findById(int id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public List<Student> findByCourse(String course) {
        return students.stream()
                .filter(s -> s.getCourse().equalsIgnoreCase(course))
                .collect(Collectors.toList());
    }

    public List<Student> findByName(String name) {
        String lower = name.toLowerCase();
        return students.stream()
                .filter(s -> s.getFirstName().toLowerCase().contains(lower)
                        || s.getLastName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public boolean deleteById(int id) {
        return students.removeIf(s -> s.getId() == id);
    }

    public int count() {
        return students.size();
    }
}
