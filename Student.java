package com.student.model;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String course;
    private double gpa;

    public Student() {}

    public Student(int id, String firstName, String lastName, String email, int age, String course, double gpa) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    // Getters
    public int getId()          { return id; }
    public String getFirstName(){ return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail()    { return email; }
    public int getAge()         { return age; }
    public String getCourse()   { return course; }
    public double getGpa()      { return gpa; }

    // Setters
    public void setId(int id)               { this.id = id; }
    public void setFirstName(String fn)     { this.firstName = fn; }
    public void setLastName(String ln)      { this.lastName = ln; }
    public void setEmail(String email)      { this.email = email; }
    public void setAge(int age)             { this.age = age; }
    public void setCourse(String course)    { this.course = course; }
    public void setGpa(double gpa)          { this.gpa = gpa; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s %s', email='%s', age=%d, course='%s', gpa=%.2f}",
                id, firstName, lastName, email, age, course, gpa);
    }
}
