/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package technologies;

/**
 *
 * @author Lenovo
 */
public class Technology {
    private int TechnologySupply_id;
    private String name;
    private String quantity;
    private String preferences;
    
    public Technology(int TechnologySupply_id, String name, String quantity, String preferences) {
        this.TechnologySupply_id = TechnologySupply_id;
        this.name = name;
        this.quantity = quantity;
        this.preferences = preferences;
    }

    public int getTechnologySupply_id() {
        return TechnologySupply_id;
    }

    public void setTechnologySupply_id(int TechnologySupply_id) {
        this.TechnologySupply_id = TechnologySupply_id;
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