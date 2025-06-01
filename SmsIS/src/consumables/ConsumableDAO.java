/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package consumables;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface ConsumableDAO {
    public boolean create(Consumable consumable);
    public Consumable read_one(int consumableSupply_id);
    public ArrayList<Consumable> read_all();
    public boolean update(int consumableSupply_id, Consumable consumable);
    public boolean delete(int consumableSupply_id);
    public ArrayList<Consumable> search(String str);
}