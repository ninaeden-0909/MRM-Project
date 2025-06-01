/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package others;

/**
 *
 * @author Lenovo
 */
public class Others {
    private int OthersSupply_id;
    private String name;
    private String quantity;
    private String preferences;
    
    public Others(int OthersSupply_id, String name, String quantity, String preferences) {
        this.OthersSupply_id = OthersSupply_id;
        this.name = name;
        this.quantity = quantity;
        this.preferences = preferences;
    }

    public int getOthersSupply_id() {
        return OthersSupply_id;
    }

    public void setOthersSupply_id(int OthersSupply_id) {
        this.OthersSupply_id = OthersSupply_id;
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