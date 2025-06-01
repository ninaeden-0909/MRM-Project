/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipments;

/**
 *
 * @author Lenovo
 */
public class Equipment {
    private int EquipmentSupply_id;
    private String name;
    private String quantity;
    private String preferences;
    
    public Equipment(int EquipmentSupply_id, String name, String quantity, String preferences) {
        this.EquipmentSupply_id = EquipmentSupply_id;
        this.name = name;
        this.quantity = quantity;
        this.preferences = preferences;
    }

    public int getEquipmentSupply_id() {
        return EquipmentSupply_id;
    }

    public void setEquipmentSupply_id(int EquipmentSupply_id) {
        this.EquipmentSupply_id = EquipmentSupply_id;
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