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
    String query = "INSERT INTO all_requests (user_id, request_info, request_type, quantity, description, purpose, requested_by, location, priority, date_created) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, request.getUser_id());
        pstmt.setString(2, request.getRequest_info());   
        pstmt.setString(3, request.getRequest_type());  
        pstmt.setString(4, request.getQuantity());
        pstmt.setString(5, request.getDescription());
        pstmt.setString(6, request.getPurpose());
        pstmt.setString(7, request.getRequested_by());
        pstmt.setString(8, request.getLocation());
        pstmt.setString(9, request.getPriority());

        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    
    public Request read_one(int request_id) {
    String query = "SELECT * FROM all_requests WHERE request_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, request_id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Request(
                rs.getInt("request_id"),
                rs.getInt("user_id"),
                rs.getString("request_info"),
                rs.getString("request_type"),
                rs.getString("quantity"),
                rs.getString("description"),
                rs.getString("purpose"),
                rs.getString("requested_by"),
                rs.getString("location"),
                rs.getString("priority"),
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
               Request request = new Request(
            rs.getInt("request_id"),
            rs.getInt("user_id"),
            rs.getString("request_info"),
            rs.getString("request_type"),
            rs.getString("quantity"),
            rs.getString("description"),
            rs.getString("purpose"),
            rs.getString("requested_by"),
            rs.getString("location"),
            rs.getString("priority"),
            rs.getString("date_created")
            );
     
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    
    public ArrayList<Request> searchRequest(String str) {
    ArrayList<Request> requests = new ArrayList<>();

        String query = """
            SELECT * FROM all_requests
            WHERE request_id NOT IN (
                SELECT DISTINCT request_id FROM responses
            ) AND requested_by LIKE ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + str + "%"); 
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                requests.add(new Request(
                    rs.getInt("request_id"),
                    rs.getInt("user_id"),
                    rs.getString("request_info"),
                    rs.getString("request_type"),
                    rs.getString("quantity"),
                    rs.getString("description"),
                    rs.getString("purpose"),
                    rs.getString("requested_by"),
                    rs.getString("location"),
                    rs.getString("priority"),
                    rs.getString("date_created")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

 
    public ArrayList<Request> readFiltered(String type, String priority) {
        ArrayList<Request> requests = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM all_requests WHERE request_id NOT IN "
                + "(SELECT DISTINCT request_id FROM responses)");

        if (!type.equals("All")) {
        query.append(" AND request_type = ?");
        }
        if (!priority.equals("All")) {
        query.append(" AND priority = ?");
        }
        try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

        int index = 1;
        if (!type.equals("All")) {
            pstmt.setString(index++, type);
        }
        if (!priority.equals("All")) {
            pstmt.setString(index++, priority);
        }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Request request = new Request(
                    rs.getInt("request_id"),
                    rs.getInt("user_id"),
                    rs.getString("request_info"),
                    rs.getString("request_type"),
                    rs.getString("quantity"),
                    rs.getString("description"),
                    rs.getString("purpose"),
                    rs.getString("requested_by"),
                    rs.getString("location"),
                    rs.getString("priority"),
                    rs.getString("date_created")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    
    public boolean approveRequest(int requestId, String decision, String remarks, int approvedBy) {
        String sql = "UPDATE all_requests SET status = ?, remarks = ?, approved_by = ?, date_approved = NOW() WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, decision);
            stmt.setString(2, remarks);
            stmt.setInt(3, approvedBy);
            stmt.setInt(4, requestId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<Request> readByStatus(String status) {
        ArrayList<Request> requests = new ArrayList<>();
    
        try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt;
        String query;

        if (status.equals("Pending")) {
            // Get requests with no responses = Pending
            query = "SELECT * FROM all_requests WHERE request_id NOT IN (SELECT DISTINCT request_id FROM responses)";
            pstmt = conn.prepareStatement(query);
        } else {
            // Get latest status from responses
            query = """
                SELECT ar.*
                FROM all_requests ar
                JOIN (
                    SELECT request_id, MAX(date_responded) AS latest
                    FROM responses
                    GROUP BY request_id
                ) lr ON ar.request_id = lr.request_id
                JOIN responses r ON r.request_id = lr.request_id AND r.date_responded = lr.latest
                WHERE r.status_update = ?
            """;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, status);
        }

        ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {
               Request request = new Request(
                   rs.getInt("request_id"),
                   rs.getInt("user_id"),
                   rs.getString("request_info"),
                   rs.getString("request_type"),
                   rs.getString("quantity"),
                   rs.getString("description"),
                   rs.getString("purpose"),
                   rs.getString("requested_by"),
                   rs.getString("location"),
                   rs.getString("priority"),
                   rs.getString("date_created"));
               requests.add(request);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    
    public ArrayList<Request> readByNoResponseOrStatus(String status) {
    ArrayList<Request> requests = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = """
                SELECT ar.*
                FROM all_requests ar
                LEFT JOIN (
                    SELECT request_id, MAX(date_responded) AS latest
                    FROM responses
                    GROUP BY request_id
                ) lr ON ar.request_id = lr.request_id
                LEFT JOIN responses r ON r.request_id = lr.request_id AND r.date_responded = lr.latest
                WHERE r.status_update = ? OR r.status_update IS NULL
            """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Request req = new Request(
                    rs.getInt("request_id"),
                    rs.getInt("user_id"),
                    rs.getString("request_info"),
                    rs.getString("request_type"),
                    rs.getString("quantity"),
                    rs.getString("description"),
                    rs.getString("purpose"),
                    rs.getString("requested_by"),
                    rs.getString("location"),
                    rs.getString("priority"),
                    rs.getString("date_created")
                );
                requests.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    
    public boolean insertCompletionResponse(int requestId, int responderId, String comment) {
        String sql = "INSERT INTO responses (request_id, responder_id, comment, status_update, date_responded) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, requestId);
            stmt.setInt(2, responderId);
            stmt.setString(3, comment);
            stmt.setString(4, "Completed");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int countByStatus(String status) {
    int count = 0;
    String sql;
    PreparedStatement stmt;

        try (Connection conn = DBConnection.getConnection()) {
            if (status.equalsIgnoreCase("Pending")) {
                // Requests without any response
                sql = "SELECT COUNT(*) FROM all_requests WHERE request_id NOT IN (SELECT DISTINCT request_id FROM responses)";
                stmt = conn.prepareStatement(sql);
            } else {
                // Get latest status from responses
                sql = """
                    SELECT COUNT(*) FROM all_requests ar
                    JOIN (
                        SELECT request_id, MAX(date_responded) AS latest
                        FROM responses
                        GROUP BY request_id
                    ) lr ON ar.request_id = lr.request_id
                    JOIN responses r ON r.request_id = lr.request_id AND r.date_responded = lr.latest
                    WHERE r.status_update = ?
                """;
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, status);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    
    public ArrayList<Request> readRequestsOfOthers(int user_id) {
    ArrayList<Request> requests = new ArrayList<>();
    String sql = "SELECT * FROM all_requests WHERE user_id != ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Request request = new Request(
                    rs.getInt("request_id"),
                    rs.getInt("user_id"),
                    rs.getString("request_info"),
                    rs.getString("request_type"),
                    rs.getString("quantity"),
                    rs.getString("description"),
                    rs.getString("purpose"),
                    rs.getString("requested_by"),
                    rs.getString("location"),
                    rs.getString("priority"),
                    rs.getString("date_created")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

}
    