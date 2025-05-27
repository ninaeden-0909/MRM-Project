/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supplies;
import database.DBConnection;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
/**
 *
 * @author Lenovo
 */
public class SupplyDAOImpl {
    public boolean create(Supply supply){
        String query = "INSERT INTO supplies (user_id, request_info, quantity,description, purpose,location,requested_by, priority,date_of_use, date_created)VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, supply.getUser_id());
            pstmt.setString(2, supply.getRequest_info());
            pstmt.setInt(3, supply.getQuantity());
            pstmt.setString(4, supply.getDescription());
            pstmt.setString(5, supply.getPurpose());
            pstmt.setString(6, supply.getLocation());
            pstmt.setString(7, supply.getRequested_by());
            pstmt.setString(8, supply.getPriority());
            pstmt.setString(9, supply.getDate_of_use());
            pstmt.setString(10,LocalDateTime.now().toString());
            pstmt.executeUpdate();
                        
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
}
