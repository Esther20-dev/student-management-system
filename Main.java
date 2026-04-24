package com.student;

import com.student.model.Student;
import com.student.repository.StudentRepository;
import com.student.service.StudentService;
import com.student.util.ConsoleHelper;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final StudentService service = new StudentService(new StudentRepository());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   STUDENT MANAGEMENT SYSTEM  v1.0      ║");
        System.out.println("╚════════════════════════════════════════╝");

        // Seed with sample data
        seedData();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> viewAllStudents();
                case "3" -> searchStudent();
                case "4" -> updateStudent();
                case "5" -> deleteStudent();
                case "6" -> viewTopStudents();
                case "7" -> viewStatistics();
                case "0" -> {
                    System.out.println("\n  Goodbye! Kwaheri!");
                    running = false;
                }
                default  -> ConsoleHelper.printError("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n┌─────────────────────────────┐");
        System.out.println("│           MENU              │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│  1. Add Student             │");
        System.out.println("│  2. View All Students       │");
        System.out.println("│  3. Search Student          │");
        System.out.println("│  4. Update Student          │");
        System.out.println("│  5. Delete Student          │");
        System.out.println("│  6. View Top Students       │");
        System.out.println("│  7. View Statistics         │");
        System.out.println("│  0. Exit                    │");
        System.out.println("└─────────────────────────────┘");
        System.out.print("Choose an option: ");
    }

    private static void addStudent() {
        ConsoleHelper.printHeader("ADD NEW STUDENT");
        try {
            System.out.print("First Name   : ");
            String fn = scanner.nextLine().trim();
            System.out.print("Last Name    : ");
            String ln = scanner.nextLine().trim();
            System.out.print("Email        : ");
            String email = scanner.nextLine().trim();
            System.out.print("Age          : ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Course       : ");
            String course = scanner.nextLine().trim();
            System.out.print("GPA (0-4.0)  : ");
            double gpa = Double.parseDouble(scanner.nextLine().trim());

            Student s = service.addStudent(fn, ln, email, age, course, gpa);
            ConsoleHelper.printSuccess("Student added with ID: " + s.getId());
        } catch (NumberFormatException e) {
            ConsoleHelper.printError("Invalid number format.");
        } catch (IllegalArgumentException e) {
            ConsoleHelper.printError(e.getMessage());
        }
    }

    private static void viewAllStudents() {
        ConsoleHelper.printHeader("ALL STUDENTS");
        ConsoleHelper.printStudentTable(service.getAllStudents());
    }

    private static void searchStudent() {
        ConsoleHelper.printHeader("SEARCH STUDENT");
        System.out.println("  1. Search by ID");
        System.out.println("  2. Search by Name");
        System.out.println("  3. Search by Course");
        System.out.print("Choose: ");
        String opt = scanner.nextLine().trim();

        switch (opt) {
            case "1" -> {
                System.out.print("Enter ID: ");
                try {
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    Optional<Student> s = service.getStudentById(id);
                    if (s.isPresent()) {
                        ConsoleHelper.printStudentTable(List.of(s.get()));
                    } else {
                        ConsoleHelper.printError("No student found with ID " + id);
                    }
                } catch (NumberFormatException e) {
                    ConsoleHelper.printError("Invalid ID.");
                }
            }
            case "2" -> {
                System.out.print("Enter Name: ");
                String name = scanner.nextLine().trim();
                ConsoleHelper.printStudentTable(service.searchByName(name));
            }
            case "3" -> {
                System.out.print("Enter Course: ");
                String course = scanner.nextLine().trim();
                ConsoleHelper.printStudentTable(service.getStudentsByCourse(course));
            }
            default -> ConsoleHelper.printError("Invalid option.");
        }
    }

    private static void updateStudent() {
        ConsoleHelper.printHeader("UPDATE STUDENT");
        System.out.print("Enter Student ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Student> existing = service.getStudentById(id);
            if (existing.isEmpty()) {
                ConsoleHelper.printError("Student not found.");
                return;
            }
            Student old = existing.get();
            System.out.println("  Current: " + old);
            System.out.println("  (Press Enter to keep current value)");

            System.out.print("First Name [" + old.getFirstName() + "]: ");
            String fn = scanner.nextLine().trim();
            if (fn.isEmpty()) fn = old.getFirstName();

            System.out.print("Last Name [" + old.getLastName() + "]: ");
            String ln = scanner.nextLine().trim();
            if (ln.isEmpty()) ln = old.getLastName();

            System.out.print("Email [" + old.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) email = old.getEmail();

            System.out.print("Age [" + old.getAge() + "]: ");
            String ageStr = scanner.nextLine().trim();
            int age = ageStr.isEmpty() ? old.getAge() : Integer.parseInt(ageStr);

            System.out.print("Course [" + old.getCourse() + "]: ");
            String course = scanner.nextLine().trim();
            if (course.isEmpty()) course = old.getCourse();

            System.out.print("GPA [" + old.getGpa() + "]: ");
            String gpaStr = scanner.nextLine().trim();
            double gpa = gpaStr.isEmpty() ? old.getGpa() : Double.parseDouble(gpaStr);

            service.updateStudent(id, fn, ln, email, age, course, gpa);
            ConsoleHelper.printSuccess("Student updated successfully.");
        } catch (NumberFormatException e) {
            ConsoleHelper.printError("Invalid number format.");
        } catch (IllegalArgumentException e) {
            ConsoleHelper.printError(e.getMessage());
        }
    }

    private static void deleteStudent() {
        ConsoleHelper.printHeader("DELETE STUDENT");
        System.out.print("Enter Student ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            service.removeStudent(id);
            ConsoleHelper.printSuccess("Student with ID " + id + " has been deleted.");
        } catch (NumberFormatException e) {
            ConsoleHelper.printError("Invalid ID.");
        } catch (IllegalArgumentException e) {
            ConsoleHelper.printError(e.getMessage());
        }
    }

    private static void viewTopStudents() {
        ConsoleHelper.printHeader("TOP 3 STUDENTS BY GPA");
        ConsoleHelper.printStudentTable(service.getTopStudents(3));
    }

    private static void viewStatistics() {
        ConsoleHelper.printHeader("STATISTICS");
        ConsoleHelper.printStats(service.getTotalStudents(), service.getAverageGpa());
    }

    private static void seedData() {
        service.addStudent("Amina",  "Hassan",   "amina@uni.ac.tz",  20, "Computer Science", 3.8);
        service.addStudent("Juma",   "Omari",    "juma@uni.ac.tz",   22, "Engineering",      3.5);
        service.addStudent("Fatuma", "Kimani",   "fatuma@uni.ac.tz", 21, "Computer Science", 3.9);
        service.addStudent("David",  "Mwangi",   "david@uni.ac.tz",  23, "Business",         2.9);
        service.addStudent("Grace",  "Msangi",   "grace@uni.ac.tz",  19, "Medicine",         3.7);
    }
}
