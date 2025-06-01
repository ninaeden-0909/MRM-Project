/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consumables;
import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ConsumableDAOImpl implements ConsumableDAO{
    public boolean create(Consumable consumable) {
        String query = "INSERT INTO consumables (name, quantity, preferences) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, consumable.getName());
            pstmt.setString(2, consumable.getQuantity());
            pstmt.setString(3, consumable.getPreferences());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Consumable read_one(int consumableSupply_id) {
        Consumable consumable = null;
        String query = "SELECT * FROM consumables WHERE consumableSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, consumableSupply_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                consumable = new Consumable(
                    rs.getInt("consumableSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                );
            }

            return consumable;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Consumable> read_all() {
        ArrayList<Consumable> consumables = new ArrayList<>();
        String query = "SELECT * FROM consumables";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                consumables.add(new Consumable(
                    rs.getInt("consumableSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return consumables;

        } catch (SQLException e) {
            e.printStackTrace();
            return consumables;
        }
    }

    public boolean update(int consumableSupply_id, Consumable consumable) {
        String query = "UPDATE consumables SET name = ?, quantity = ?, preferences = ? WHERE consumableSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, consumable.getName());
            pstmt.setString(2, consumable.getQuantity());
            pstmt.setString(3, consumable.getPreferences());
            pstmt.setInt(4, consumableSupply_id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int consumableSupply_id) {
        String query = "DELETE FROM consumables WHERE consumableSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, consumableSupply_id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Consumable> search(String str) {
        ArrayList<Consumable> consumables = new ArrayList<>();
        String query = "SELECT * FROM consumables WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + str + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                consumables.add(new Consumable(
                    rs.getInt("consumableSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return consumables;

        } catch (SQLException e) {
            e.printStackTrace();
            return consumables;
        }
    }  
}