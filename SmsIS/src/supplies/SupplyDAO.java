/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package supplies;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface SupplyDAO {
    public boolean create(Supply supply);
    public Supply read_one(int supply_id);
    public ArrayList<Supply> read_all();
    public boolean update(int supply_id, Supply supply);
    public boolean delete(int supply_id);
    public ArrayList<Supply> search(String str);
}
