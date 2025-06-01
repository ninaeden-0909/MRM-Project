/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supplies;

/**
 *
 * @author Lenovo
 */
public class Supply {
    private int supply_id;
    private String name;
    private String quantity;
    private String preferences;

    public Supply(int supply_id, String name, String quantity, String preferences) {
        this.supply_id = supply_id;
        this.name = name;
        this.quantity = quantity;
        this.preferences = preferences;
    }

    public int getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(int supply_id) {
        this.supply_id = supply_id;
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
