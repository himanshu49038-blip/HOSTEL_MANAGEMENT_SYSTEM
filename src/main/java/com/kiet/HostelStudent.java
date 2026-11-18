
package com.kiet;

public class HostelStudent {

    private int studentId;
    private String studentName;
    private String rollNumber;
    private String hostelName;
    private String roomNumber;
    private double fee;

    public HostelStudent(int studentId, String studentName,
                         String rollNumber, String hostelName,
                         String roomNumber, double fee) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.hostelName = hostelName;
        this.roomNumber = roomNumber;
        this.fee = fee;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getHostelName() {
        return hostelName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void display() {

        System.out.println("Student ID: " + studentId);
        System.out.println("Student Name: " + studentName);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Hostel Name: " + hostelName);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Hostel Fee: " + fee);
    }
}
