
package com.kiet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class HostelManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection con = DBConnection.getConnection()) {

            System.out.println("Database Connected Successfully!");

            HostelStudentDAO dao = new HostelStudentDAO(con);

            int choice;

            do {

                System.out.println("\n===== KIET HOSTEL MANAGEMENT SYSTEM =====");
                System.out.println("1. Insert Hostel Student Records");
                System.out.println("2. Retrieve Students by Hostel and Fee");
                System.out.println("3. Update Hostel Fee");
                System.out.println("4. Search and Update Room Number");
                System.out.println("5. Display Total Fee and Delete Student");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {


                    case 1:

                        int more;

                        do {

                            System.out.println("\n--- Insert Hostel Student Record ---");

                            System.out.print("Enter Student ID: ");
                            int id = Integer.parseInt(sc.nextLine());

                            System.out.print("Enter Student Name: ");
                            String name = sc.nextLine();

                            System.out.print("Enter Roll Number: ");
                            String roll = sc.nextLine();

                            System.out.print("Enter Hostel Name: ");
                            String hostel = sc.nextLine();

                            System.out.print("Enter Room Number: ");
                            String room = sc.nextLine();

                            System.out.print("Enter Hostel Fee: ");
                            double fee = Double.parseDouble(sc.nextLine());

                            HostelStudent student = new HostelStudent(
                                    id, name, roll, hostel, room, fee
                            );

                            if (dao.insertStudent(student)) {
                                System.out.println("\nStudent Inserted Successfully!");
                            } else {
                                System.out.println("\nDuplicate Student ID! Record Not Inserted.");
                            }

                            System.out.println("\n1. Insert Another Student");
                            System.out.println("2. Return to Main Menu");

                            System.out.print("Enter your choice: ");
                            more = Integer.parseInt(sc.nextLine());

                        } while (more == 1);

                        break;




                    case 2:

                        System.out.print("Enter Hostel Name: ");
                        String hostel = sc.nextLine();

                        List<HostelStudent> students =
                                dao.getStudentsByHostel(hostel);

                        if (students.isEmpty()) {
                            System.out.println("No matching students found!");
                        } else {

                            System.out.println("\n--- Student Records ---");

                            for (HostelStudent s : students) {
                                s.display();
                                System.out.println("----------------------");
                            }
                        }

                        break;


                    case 3:

                        System.out.print("Enter Student ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter New Hostel Fee: ");
                        double fee = Double.parseDouble(sc.nextLine());

                        if (dao.updateFee(id, fee)) {
                            System.out.println("Hostel Fee Updated Successfully!");
                        } else {
                            System.out.println("Student Record Does Not Exist!");
                        }

                        break;


                    case 4:

                        System.out.print("Enter Student ID: ");
                        int studentId = Integer.parseInt(sc.nextLine());

                        HostelStudent student =
                                dao.getStudentById(studentId);

                        if (student != null) {

                            System.out.println("\nExisting Student Details:");
                            student.display();

                            System.out.print("\nEnter New Room Number: ");
                            String room = sc.nextLine();

                            dao.updateRoom(studentId, room);

                            System.out.println("\nUpdated Student Details:");

                            HostelStudent updatedStudent =
                                    dao.getStudentById(studentId);

                            updatedStudent.display();

                        } else {
                            System.out.println("Student Record Does Not Exist!");
                        }

                        break;


                    case 5:

                        double totalFee = dao.getTotalFee();

                        System.out.println(
                                "\nTotal Hostel Fee Collected: Rs. " + totalFee
                        );

                        System.out.print("Enter Student ID to Delete: ");
                        int deleteId = Integer.parseInt(sc.nextLine());

                        if (dao.deleteStudent(deleteId)) {
                            System.out.println("Student Deleted Successfully!");
                        } else {
                            System.out.println(
                                    "Cannot Delete! Student does not exist " +
                                            "or fee is not fully paid."
                            );
                        }

                        break;


                    case 6:

                        System.out.println("Exiting Hostel Management System...");
                        break;


                    default:

                        System.out.println("Invalid Choice!");
                }

            } while (choice != 6);

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid numeric input.");

        } finally {
            sc.close();
        }
    }
}
