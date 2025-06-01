/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package others;
import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class OthersDAOImpl {
    public boolean create(Others others) {
        String query = "INSERT INTO others (name, quantity, preferences) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, others.getName());
            pstmt.setString(2, others.getQuantity());
            pstmt.setString(3, others.getPreferences());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Others read_one(int othersSupply_id) {
        Others others = null;
        String query = "SELECT * FROM others WHERE othersSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, othersSupply_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                others = new Others(
                    rs.getInt("othersSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                );
            }

            return others;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Others> read_all() {
        ArrayList<Others> othersList = new ArrayList<>();
        String query = "SELECT * FROM others";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                othersList.add(new Others(
                    rs.getInt("othersSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return othersList;

        } catch (SQLException e) {
            e.printStackTrace();
            return othersList;
        }
    }

    public boolean update(int othersSupply_id, Others others) {
        String query = "UPDATE others SET name = ?, quantity = ?, preferences = ? WHERE othersSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, others.getName());
            pstmt.setString(2, others.getQuantity());
            pstmt.setString(3, others.getPreferences());
            pstmt.setInt(4, othersSupply_id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int othersSupply_id) {
        String query = "DELETE FROM others WHERE othersSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, othersSupply_id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Others> search(String str) {
        ArrayList<Others> othersList = new ArrayList<>();
        String query = "SELECT * FROM others WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + str + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                othersList.add(new Others(
                    rs.getInt("othersSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return othersList;

        } catch (SQLException e) {
            e.printStackTrace();
            return othersList;
        }
    }
}
