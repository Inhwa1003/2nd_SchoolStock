package com.school.schoolstock.domain.student.vo;

import java.time.LocalDate;

public class Students {

    private String studentId;
    private String password;
    private String name;
    private int grade;
    private String className;
    private int studentNumber;
    private LocalDate registerYear;
    private int totalCoupon;
    private int totalPoint;

    public Students() {
    }

    public Students(String studentId) {
        this.studentId = studentId;
    }

    public Students(String studentId, String password, String name, int grade,
                    String className, int studentNumber, LocalDate registerYear,
                    int totalCoupon, int totalPoint) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.grade = grade;
        this.className = className;
        this.studentNumber = studentNumber;
        this.registerYear = registerYear;
        this.totalCoupon = totalCoupon;
        this.totalPoint = totalPoint;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public LocalDate getRegisterYear() {
        return registerYear;
    }

    public void setRegisterYear(LocalDate registerYear) {
        this.registerYear = registerYear;
    }

    public int getTotalCoupon() {
        return totalCoupon;
    }

    public void setTotalCoupon(int totalCoupon) {
        this.totalCoupon = totalCoupon;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    @Override
    public String toString() {
        return "Students{" +
                "studentId='" + studentId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", className='" + className + '\'' +
                ", studentNumber=" + studentNumber +
                ", registerYear=" + registerYear +
                ", totalCoupon=" + totalCoupon +
                ", totalPoint=" + totalPoint +
                '}';
    }
}