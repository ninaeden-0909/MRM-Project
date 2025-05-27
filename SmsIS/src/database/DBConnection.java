/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import java.sql.*;

/**
 *
 * @author Lenovo
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/smsis";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch(SQLException e){
            return null;
        }
    }  
}
