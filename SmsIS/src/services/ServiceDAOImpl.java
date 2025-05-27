/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import database.DBConnection;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
/**
 *
 * @author Lenovo
 */
public class ServiceDAOImpl implements ServiceDAO {
     public boolean create(Service service){
        String query = "INSERT INTO services (user_id, request_info, description, location, priority, date_created)VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, service.getUser_id());
            pstmt.setString(2, service.getRequest_info());
            pstmt.setString(3, service.getDescription());
            pstmt.setString(4, service.getLocation());
            pstmt.setString(5, service.getPriority());
            pstmt.setString(6,LocalDateTime.now().toString());
            pstmt.executeUpdate();
                        
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
}
