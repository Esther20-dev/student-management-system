package com.student;

import com.student.model.Student;
import com.student.repository.StudentRepository;
import com.student.service.StudentService;

import java.util.List;
import java.util.Optional;

/**
 * Simple unit tests (no JUnit dependency required - runs standalone)
 */
public class StudentServiceTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("Running tests...\n");

        testAddStudent();
        testAddStudentInvalidEmail();
        testAddStudentInvalidGpa();
        testGetStudentById();
        testUpdateStudent();
        testDeleteStudent();
        testSearchByName();
        testGetTopStudents();
        testGetAverageGpa();

        System.out.println("\n==========================");
        System.out.printf("  Passed: %d | Failed: %d%n", passed, failed);
        System.out.println("==========================");
    }

    static void testAddStudent() {
        StudentService svc = freshService();
        Student s = svc.addStudent("Amina", "Hassan", "amina@test.com", 20, "CS", 3.8);
        assertTrue("Add student - has ID", s.getId() > 0);
        assertEquals("Add student - full name", "Amina Hassan", s.getFullName());
    }

    static void testAddStudentInvalidEmail() {
        StudentService svc = freshService();
        try {
            svc.addStudent("John", "Doe", "invalidemail", 20, "CS", 3.0);
            fail("Should throw on invalid email");
        } catch (IllegalArgumentException e) {
            pass("Reject invalid email");
        }
    }

    static void testAddStudentInvalidGpa() {
        StudentService svc = freshService();
        try {
            svc.addStudent("John", "Doe", "john@test.com", 20, "CS", 5.0);
            fail("Should throw on GPA > 4.0");
        } catch (IllegalArgumentException e) {
            pass("Reject GPA > 4.0");
        }
    }

    static void testGetStudentById() {
        StudentService svc = freshService();
        Student added = svc.addStudent("Juma", "Omari", "juma@test.com", 22, "Eng", 3.5);
        Optional<Student> found = svc.getStudentById(added.getId());
        assertTrue("Find by ID", found.isPresent());
        assertEquals("Find by ID - correct student", "Juma", found.get().getFirstName());
    }

    static void testUpdateStudent() {
        StudentService svc = freshService();
        Student s = svc.addStudent("Fatuma", "Ali", "fatuma@test.com", 21, "CS", 3.6);
        svc.updateStudent(s.getId(), "Fatuma", "Ali", "fatuma@test.com", 21, "CS", 3.9);
        Optional<Student> updated = svc.getStudentById(s.getId());
        assertTrue("Update student", updated.isPresent());
        assertEquals("Updated GPA", "3.90", String.format("%.2f", updated.get().getGpa()));
    }

    static void testDeleteStudent() {
        StudentService svc = freshService();
        Student s = svc.addStudent("David", "Mwangi", "david@test.com", 23, "Biz", 2.9);
        svc.removeStudent(s.getId());
        assertTrue("Student removed", svc.getStudentById(s.getId()).isEmpty());
    }

    static void testSearchByName() {
        StudentService svc = freshService();
        svc.addStudent("Grace", "Msangi", "grace@test.com", 19, "Med", 3.7);
        List<Student> results = svc.searchByName("grace");
        assertTrue("Search by name", !results.isEmpty());
        assertEquals("Search finds correct student", "Grace", results.get(0).getFirstName());
    }

    static void testGetTopStudents() {
        StudentService svc = freshService();
        svc.addStudent("A", "A", "a@t.com", 20, "CS", 3.5);
        svc.addStudent("B", "B", "b@t.com", 20, "CS", 4.0);
        svc.addStudent("C", "C", "c@t.com", 20, "CS", 2.5);
        List<Student> top = svc.getTopStudents(1);
        assertEquals("Top student has highest GPA", "4.00", String.format("%.2f", top.get(0).getGpa()));
    }

    static void testGetAverageGpa() {
        StudentService svc = freshService();
        svc.addStudent("A", "A", "a@t.com", 20, "CS", 3.0);
        svc.addStudent("B", "B", "b@t.com", 20, "CS", 4.0);
        double avg = svc.getAverageGpa();
        assertEquals("Average GPA", "3.50", String.format("%.2f", avg));
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    static StudentService freshService() {
        return new StudentService(new StudentRepository());
    }

    static void assertTrue(String name, boolean condition) {
        if (condition) pass(name); else fail(name + " (expected true)");
    }

    static void assertEquals(String name, String expected, String actual) {
        if (expected.equals(actual)) pass(name); else fail(name + " (expected='" + expected + "', got='" + actual + "')");
    }

    static void pass(String name) { passed++; System.out.println("  [PASS] " + name); }
    static void fail(String name) { failed++; System.out.println("  [FAIL] " + name); }
}
