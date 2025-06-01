/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package session;

/**
 *
 * @author Lenovo
 */
public class LoggedInUser {
    private static int userId;
    private static String username;
    private static String role;

    public static void setUser(int id, String uname, String userRole) {
        userId = id;
        username = uname;
        role = userRole;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static void logout() {
        userId = 0;
        username = null;
        role = null;
    }
}
