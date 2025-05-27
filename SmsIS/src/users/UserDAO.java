/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package users;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface UserDAO {
    public boolean create(User user);
    public User read_one (int user_id);
    public ArrayList<User>read_all ();
    public boolean delete(int user_id);
    public boolean update(User user);
    public ArrayList<User> searchUser(String str);
}
