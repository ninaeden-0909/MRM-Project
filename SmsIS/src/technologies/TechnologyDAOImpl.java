/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package technologies;
import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class TechnologyDAOImpl {
    public boolean create(Technology technology) {
        String query = "INSERT INTO technology (name, quantity, preferences) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, technology.getName());
            pstmt.setString(2, technology.getQuantity());
            pstmt.setString(3, technology.getPreferences());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Technology read_one(int technologySupply_id) {
        Technology technology = null;
        String query = "SELECT * FROM technology WHERE technologySupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, technologySupply_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                technology = new Technology(
                    rs.getInt("technologySupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                );
            }

            return technology;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Technology> read_all() {
        ArrayList<Technology> technologies = new ArrayList<>();
        String query = "SELECT * FROM technology";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                technologies.add(new Technology(
                    rs.getInt("technologySupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return technologies;

        } catch (SQLException e) {
            e.printStackTrace();
            return technologies;
        }
    }

    public boolean update(int technologySupply_id, Technology technology) {
        String query = "UPDATE technology SET name = ?, quantity = ?, preferences = ? WHERE technologySupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, technology.getName());
            pstmt.setString(2, technology.getQuantity());
            pstmt.setString(3, technology.getPreferences());
            pstmt.setInt(4, technologySupply_id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int technologySupply_id) {
        String query = "DELETE FROM technology WHERE technologySupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, technologySupply_id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Technology> search(String str) {
        ArrayList<Technology> technologies = new ArrayList<>();
        String query = "SELECT * FROM technology WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + str + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                technologies.add(new Technology(
                    rs.getInt("technologySupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return technologies;

        } catch (SQLException e) {
            e.printStackTrace();
            return technologies;
        }
    }
}
