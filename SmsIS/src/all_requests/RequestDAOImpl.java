/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package all_requests;

import database.DBConnection;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author Lenovo
 */
public class RequestDAOImpl implements RequestDAO{
    public boolean create(Request request) {
        String query = "INSERT INTO all_requests (user_id, request_type, request_info, quantity, description, purpose, requested_by, location, date_of_use, priority, date_created) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, request.getUser_id());
            pstmt.setString(2, request.getRequest_type());
            pstmt.setString(3, request.getRequest_info());
            pstmt.setInt(4, request.getQuantity());
            pstmt.setString(5, request.getDescription());
            pstmt.setString(6, request.getPurpose());
            pstmt.setString(7, request.getRequested_by());
            pstmt.setString(8, request.getLocation());
            pstmt.setString(9, request.getDate_of_use());
            pstmt.setString(10, request.getPriority());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Request read_one(int id) {
    String query = "SELECT * FROM all_requests WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Request(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("request_type"),
                rs.getString("request_info"),
                rs.getInt("quantity"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getString("priority"),
                rs.getString("purpose"),
                rs.getString("requested_by"),
                rs.getString("date_of_use"),
                rs.getString("date_created")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    public ArrayList <Request> read_all() {
        ArrayList<Request> requests = new ArrayList<Request>();
        String sql = "SELECT * FROM all_requests";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Request request = new Request(rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("request_type"),
                        rs.getString("request_info"),
                        rs.getObject("quantity") != null ? rs.getInt("quantity") : null,
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("purpose"),
                        rs.getString("priority"),
                        rs.getString("requested_by"),
                        rs.getString("date_of_use"),
                        rs.getString("date_created"));
                
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

}
