/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responses;
import database.DBConnection;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
/**
 *
 * @author Lenovo
 */
public class ResponseDAOImpl implements ResponseDAO{  
    public Response getResponseByUserId(int response_id){
        String query = "SELECT * FROM responses WHERE response_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, response_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Response(
                    rs.getInt("response_id"),
                    rs.getInt("request_id"),
                    rs.getInt("responder_id"),
                    rs.getString("comment"),
                    rs.getString("status_update"),
                    rs.getString("schedule_datetime"),
                    rs.getString("date_responded")
                );
            }
        } catch (SQLException e) { 
                e.printStackTrace(); 
        }
        return null;
    }
    
    
    public ArrayList<Response> getAllResponses(int user_id) {
    ArrayList<Response> responses = new ArrayList<>();
        String query = "SELECT response.* FROM responses response " +
                       "JOIN all_requests request ON response.request_id = request.request_id " +
                       "WHERE request.user_id = ? ORDER BY response.date_responded DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();  // ✅ No query argument here

            while (rs.next()) {
                Response response = new Response(
                    rs.getInt("response_id"),
                    rs.getInt("request_id"),
                    rs.getInt("responder_id"),
                    rs.getString("comment"),
                    rs.getString("status_update"),
                    rs.getString("schedule_datetime"),
                    rs.getString("date_responded")
                );
                responses.add(response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return responses;
    }
    
    
    public boolean saveResponse(Response response) {
    String sql = "INSERT INTO responses (request_id, responder_id, comment, status_update, schedule_datetime, date_responded) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, response.getRequest_id());
            stmt.setInt(2, response.getResponder_id());
            stmt.setString(3, response.getComment());
            stmt.setString(4, response.getStatus_update()); // e.g. "Approved", "Rejected"
            stmt.setTimestamp(5, Timestamp.valueOf(response.getSchedule_Datetime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean insertCompletionResponse(int request_id, int responder_id, String comment) {
    String insertSql = "INSERT INTO responses (request_id, responder_id, comment, status_update, date_responded) " +
                       "VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            insertStmt.setInt(1, request_id);
            insertStmt.setInt(2, responder_id);
            insertStmt.setString(3, comment);
            insertStmt.setString(4, "Completed");
            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                // ✅ Now update the status in all_requests table
                String updateSql = "UPDATE responses SET status_update = 'Completed' WHERE request_id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, request_id);
                    updateStmt.executeUpdate();
                }
                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public ArrayList<Response> getCompletedResponsesByUserId(int userId) {
    ArrayList<Response> completedResponses = new ArrayList<>();
        String sql = "SELECT r.response_id, r.request_id, r.responder_id, " +
                     "r.comment, r.status_update, r.schedule_datetime, r.date_responded " +
                     "FROM responses r " +
                     "JOIN all_requests a ON r.request_id = a.request_id " +
                     "WHERE a.user_id = ? AND r.status_update = 'Completed' " +
                     "ORDER BY r.date_responded DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Response response = new Response(
                    rs.getInt("response_id"),
                    rs.getInt("request_id"),
                    rs.getInt("responder_id"),
                    rs.getString("comment"),
                    rs.getString("status_update"),
                    rs.getString("schedule_datetime"),
                    rs.getString("date_responded")
                );
                completedResponses.add(response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return completedResponses;
    }  
    
    
    public String getLatestStatusByRequestId(int requestId) {
        String sql = "SELECT status_update FROM responses WHERE request_id = ? ORDER BY date_responded DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status_update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateResponseToCompleted(int request_id, String comment) {
    String sql = "UPDATE responses SET comment = ?, status_update = 'Completed', date_responded = NOW() WHERE request_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comment);
            stmt.setInt(2, request_id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}