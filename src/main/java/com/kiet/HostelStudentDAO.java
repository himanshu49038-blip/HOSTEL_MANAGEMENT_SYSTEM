
package com.kiet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HostelStudentDAO {

    private Connection con;

    public HostelStudentDAO(Connection con) {
        this.con = con;
    }

    // (a) INSERT THREE STUDENT RECORDS


// (a) INSERT STUDENT RECORD


    public boolean insertStudent(HostelStudent s) throws SQLException {

        String query = "INSERT INTO HostelStudent " +
                "(student_id, student_name, roll_number, hostel_name, room_number, fee) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, s.getStudentId());
            ps.setString(2, s.getStudentName());
            ps.setString(3, s.getRollNumber());
            ps.setString(4, s.getHostelName());
            ps.setString(5, s.getRoomNumber());
            ps.setDouble(6, s.getFee());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            if ("23000".equals(e.getSQLState())) {
                return false;
            }

            throw e;
        }
    }




    // (b) RETRIEVE STUDENTS BY HOSTEL AND FEE

    public List<HostelStudent> getStudentsByHostel(String hostel)
            throws SQLException {

        List<HostelStudent> students = new ArrayList<>();

        String query = "SELECT * FROM HostelStudent " +
                "WHERE hostel_name = ? AND fee > 50000 " +
                "ORDER BY fee DESC";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, hostel);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    HostelStudent s = new HostelStudent(
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getString("roll_number"),
                            rs.getString("hostel_name"),
                            rs.getString("room_number"),
                            rs.getDouble("fee")
                    );

                    students.add(s);
                }
            }
        }

        return students;
    }


    // (c) UPDATE HOSTEL FEE

    public boolean updateFee(int id, double fee)
            throws SQLException {

        String query =
                "UPDATE HostelStudent SET fee = ? WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDouble(1, fee);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        }
    }


    // (d) SEARCH STUDENT BY ID

    public HostelStudent getStudentById(int id)
            throws SQLException {

        String query =
                "SELECT * FROM HostelStudent WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new HostelStudent(
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getString("roll_number"),
                            rs.getString("hostel_name"),
                            rs.getString("room_number"),
                            rs.getDouble("fee")
                    );
                }
            }
        }

        return null;
    }


    // (d) UPDATE ROOM NUMBER

    public boolean updateRoom(int id, String room)
            throws SQLException {

        String query =
                "UPDATE HostelStudent SET room_number = ? WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, room);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        }
    }


    // (e) CALCULATE TOTAL HOSTEL FEE

    public double getTotalFee() throws SQLException {

        String query = "SELECT SUM(fee) AS total_fee FROM HostelStudent";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                return rs.getDouble("total_fee");
            }
        }

        return 0;
    }


    // (e) DELETE STUDENT ONLY IF FEE IS GREATER THAN ZERO

    public boolean deleteStudent(int id) throws SQLException {

        String query =
                "DELETE FROM HostelStudent WHERE student_id = ? AND fee > 0";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}
