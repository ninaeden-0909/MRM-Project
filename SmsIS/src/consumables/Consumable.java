/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consumables;

/**
 *
 * @author Lenovo
 */
public class Consumable {
    private int ConsumableSupply_id;
    private String name;
    private String quantity;
    private String preferences;

    public Consumable(int ConsumableSupply_id, String name, String quantity, String preferences) {
        this.ConsumableSupply_id = ConsumableSupply_id;
        this.name = name;
        this.quantity = quantity;
        this.preferences = preferences;
    }

    public int getConsumableSupply_id() {
        return ConsumableSupply_id;
    }

    public void setConsumableSupply_id(int ConsumableSupply_id) {
        this.ConsumableSupply_id = ConsumableSupply_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
    
    
}