package com.student.util;

import com.student.model.Student;

import java.util.List;

public class ConsoleHelper {

    public static void printHeader(String title) {
        String line = "=".repeat(60);
        System.out.println("\n" + line);
        System.out.printf("  %s%n", title);
        System.out.println(line);
    }

    public static void printStudentTable(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("  (No students found)");
            return;
        }
        System.out.printf("%-4s %-20s %-25s %-6s %-15s %-5s%n",
                "ID", "Full Name", "Email", "Age", "Course", "GPA");
        System.out.println("-".repeat(80));
        for (Student s : students) {
            System.out.printf("%-4d %-20s %-25s %-6d %-15s %-5.2f%n",
                    s.getId(), s.getFullName(), s.getEmail(),
                    s.getAge(), s.getCourse(), s.getGpa());
        }
        System.out.println("-".repeat(80));
        System.out.println("  Total: " + students.size() + " student(s)");
    }

    public static void printSuccess(String message) {
        System.out.println("  [OK] " + message);
    }

    public static void printError(String message) {
        System.out.println("  [ERROR] " + message);
    }

    public static void printStats(int total, double avgGpa) {
        System.out.printf("  Total Students : %d%n", total);
        System.out.printf("  Average GPA    : %.2f%n", avgGpa);
    }
}
