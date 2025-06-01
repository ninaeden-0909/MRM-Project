/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package equipments;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface EquipmentDAO {
    public boolean create(Equipment equipment);
    public Equipment read_one(int equipmentSupply_id);
    public ArrayList<Equipment> read_all();
    public boolean update(int equipmentSupply_id, Equipment equipment);
    public boolean delete(int equipmentSupply_id);
    public ArrayList<Equipment> search(String str);
}
