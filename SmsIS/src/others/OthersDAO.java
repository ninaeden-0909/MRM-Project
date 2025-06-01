/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package others;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface OthersDAO {
    public boolean create(Others others);
    public Others read_one(int othersSupply_id);
    public ArrayList<Others> read_all();
    public boolean update(int othersSupply_id, Others others);
    public boolean delete(int othersSupply_id);
    public ArrayList<Others> search(String str);
}