/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supplies;
import database.DBConnection;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Lenovo
 */
public class SupplyDAOImpl implements SupplyDAO{
    public boolean create(Supply supply){
        String query = "INSERT INTO supplies (name, quantity, preferences)VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, supply.getName());
            pstmt.setString(2, supply.getQuantity());
            pstmt.setString(3, supply.getPreferences());
            pstmt.executeUpdate();
                        
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public Supply read_one(int supply_id){
        Supply supply = null;
        String query = "SELECT * FROM supplies WHERE supply_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, supply_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                supply = new Supply(rs.getInt("supply_id"), rs.getString("name"), 
                        rs.getString("quantity"), rs.getString("preferences"));
            }
            
            return supply;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return supply;
        }
    }
    
    public ArrayList<Supply> read_all() {
        ArrayList<Supply> supplies = new ArrayList<>();
        String query = "SELECT * FROM supplies";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                supplies.add(new Supply(rs.getInt("supply_id"), rs.getString("name"),
                        rs.getString("quantity"), rs.getString("preferences")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplies;
    }

    
    public boolean update(int supply_id, Supply supply){
        String query = "UPDATE supplies SET name = ?, quantity = ?, preferences = ? WHERE supply_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, supply.getName());
            pstmt.setString(2, supply.getQuantity()); 
            pstmt.setString(3, supply.getPreferences());
            pstmt.setInt(4, supply_id);
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public boolean delete(int supply_id){
        String query = "DELETE FROM supplies WHERE supply_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, supply_id);
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public ArrayList<Supply> search(String str){
        ArrayList<Supply> supplies = new ArrayList<Supply>();
        String query = "SELECT * FROM supplies WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + str + "%"); 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                supplies.add(new Supply(rs.getInt("supply_id"), rs.getString("name"), 
                        rs.getString("quantity"), rs.getString("preferences")));
            }
            
            return supplies;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return supplies;
        }
    }
}
