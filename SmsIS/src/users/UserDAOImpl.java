/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

import database.DBConnection;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author Lenovo
 */

public class UserDAOImpl implements UserDAO{
    
    public boolean create(User user){
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.executeUpdate();
                        
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public User read_one(int user_id){
        User user = null;
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("username"), 
                        rs.getString("password"), rs.getString("role"));
            } 
            
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return user;
        }
    }
    
    public ArrayList<User> read_all(){
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), 
                        rs.getString("username"), 
                        rs.getString("password"), 
                        rs.getString("role")
                );
                users.add(user);
            }
            
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return users;
        }
    }
    
    public boolean update(User user) {
        String query = "UPDATE users SET  password = ?, role = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getRole()); 
            pstmt.setInt(3, user.getUser_id());
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }

    public boolean delete(int user_id){
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public ArrayList<User> searchUser(String str) {
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM users WHERE username LIKE ? OR role LIKE ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + str + "%"); 
            pstmt.setString(2, "%" + str + "%"); 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("user_id"), rs.getString("username"), 
                        rs.getString("password"), rs.getString("role")));
            }
            
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return users;
        }
    }   
}

