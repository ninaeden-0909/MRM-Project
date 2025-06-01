/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipments;
import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class EquipmentDAOImpl implements EquipmentDAO{
    public boolean create(Equipment equipment) {
        String query = "INSERT INTO equipment (name, quantity, preferences) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getQuantity());
            pstmt.setString(3, equipment.getPreferences());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Equipment read_one(int equipmentSupply_id) {
        Equipment equipment = null;
        String query = "SELECT * FROM equipment  WHERE equipmentSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, equipmentSupply_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                equipment = new Equipment(
                    rs.getInt("equipmentSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                );
            }

            return equipment;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Equipment> read_all() {
        ArrayList<Equipment> equipments = new ArrayList<>();
        String query = "SELECT * FROM equipment ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                equipments.add(new Equipment(
                    rs.getInt("equipmentSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return equipments;

        } catch (SQLException e) {
            e.printStackTrace();
            return equipments;
        }
    }

    public boolean update(int equipmentSupply_id, Equipment equipment) {
        String query = "UPDATE equipment SET name = ?, quantity = ?, preferences = ? WHERE equipmentSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getQuantity());
            pstmt.setString(3, equipment.getPreferences());
            pstmt.setInt(4, equipmentSupply_id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int equipmentSupply_id) {
        String query = "DELETE FROM equipment WHERE equipmentSupply_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, equipmentSupply_id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Equipment> search(String str) {
        ArrayList<Equipment> equipments = new ArrayList<>();
        String query = "SELECT * FROM equipment WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + str + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                equipments.add(new Equipment(
                    rs.getInt("equipmentSupply_id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("preferences")
                ));
            }

            return equipments;

        } catch (SQLException e) {
            e.printStackTrace();
            return equipments;
        }
    }
}