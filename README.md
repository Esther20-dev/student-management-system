# 🎓 Student Management System

A simple **Java console application** for managing student records. Built using clean OOP principles with a layered architecture.

## Features

- ✅ Add, update, and delete students
- ✅ View all students in a formatted table
- ✅ Search by ID, name, or course
- ✅ View top students by GPA
- ✅ View statistics (total count, average GPA)
- ✅ Input validation with meaningful error messages

## Project Structure

```
StudentManagementSystem/
├── src/
│   ├── main/java/com/student/
│   │   ├── Main.java                      # Entry point & CLI menu
│   │   ├── model/
│   │   │   └── Student.java               # Student data model
│   │   ├── repository/
│   │   │   └── StudentRepository.java     # In-memory data storage
│   │   ├── service/
│   │   │   └── StudentService.java        # Business logic & validation
│   │   └── util/
│   │       └── ConsoleHelper.java         # Console formatting helpers
│   └── test/java/com/student/
│       └── StudentServiceTest.java        # Unit tests
└── README.md
```

## Architecture

```
Main (CLI) → StudentService (Business Logic) → StudentRepository (Storage)
```

This follows a **3-layer architecture**:
- **Model** – Plain Java objects (POJOs)
- **Repository** – Data access (in-memory `ArrayList`)
- **Service** – Business rules and validation

## How to Run

### Prerequisites
- Java 11 or higher

### Compile

```bash
# From project root
find src -name "*.java" > sources.txt
javac -d out @sources.txt
```

### Run Main App

```bash
java -cp out com.student.Main
```

### Run Tests

```bash
java -cp out com.student.StudentServiceTest
```

## Sample Output

```
╔════════════════════════════════════════╗
║   STUDENT MANAGEMENT SYSTEM  v1.0      ║
╚════════════════════════════════════════╝

┌─────────────────────────────┐
│           MENU              │
├─────────────────────────────┤
│  1. Add Student             │
│  2. View All Students       │
│  3. Search Student          │
│  4. Update Student          │
│  5. Delete Student          │
│  6. View Top Students       │
│  7. View Statistics         │
│  0. Exit                    │
└─────────────────────────────┘
```

## Future Improvements

- [ ] File/database persistence (CSV or SQLite)
- [ ] JUnit 5 proper test suite
- [ ] Export to PDF/Excel
- [ ] GUI with JavaFX

## Author

Developed as a Java learning project. Feel free to fork and improve!

## License

MIT License
