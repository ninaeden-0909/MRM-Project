/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

/**
 *
 * @author Lenovo
 */
public class User {
    private int user_id;
    private String role;//admin or personnel
    private String username;
    private String password;

    public User(int user_id, String role, String username, String password) {
        this.user_id = user_id;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
