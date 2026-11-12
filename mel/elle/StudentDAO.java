
package elle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // ADD STUDENT
    public static boolean addStudent(
            int id, String name,
            String course, double grade) {

        String sql = "INSERT INTO students " +
                     "(id, name, course, grade) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn =
                DatabaseConnection.getConnection();
             PreparedStatement stmt =
                conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, course);
            stmt.setDouble(4, grade);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Database error: " + e.getMessage()
            );
            return false;
        }
    }

    // LOAD ALL STUDENTS
    public static List<StudentManagementSystem.Student>
            getAllStudents() {

        List<StudentManagementSystem.Student> students =
            new ArrayList<>();

        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection conn =
                DatabaseConnection.getConnection();
             PreparedStatement stmt =
                conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                StudentManagementSystem.Student student =
                    new StudentManagementSystem.Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getDouble("grade")
                    );

                students.add(student);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Unable to load students from MySQL.", e
            );
        }

        return students;
    }

    // UPDATE STUDENT
    public static boolean updateStudent(
            int id, String name,
            String course, double grade) {

        String sql = "UPDATE students SET " +
                     "name=?, course=?, grade=? " +
                     "WHERE id=?";

        try (Connection conn =
                DatabaseConnection.getConnection();
             PreparedStatement stmt =
                conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, course);
            stmt.setDouble(3, grade);
            stmt.setInt(4, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Database error: " + e.getMessage()
            );
            return false;
        }
    }

    // DELETE STUDENT
    public static boolean deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id=?";

        try (Connection conn =
                DatabaseConnection.getConnection();
             PreparedStatement stmt =
                conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Database error: " + e.getMessage()
            );
            return false;
        }
    }
}
