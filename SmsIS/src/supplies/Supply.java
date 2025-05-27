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
    private int request_id;
    private int user_id;
    private String request_info;
    private int quantity;
    private String description;
    private String purpose;
    private String location;
    private String requested_by;
    private String priority;
    private String date_of_use;
    private String date_created;

    public Supply(int request_id, int user_id, String request_info, int quantity, String description, String purpose, String location, String requested_by, String priority, String date_of_use, String date_created) {
        this.request_id = request_id;
        this.user_id = user_id;
        this.request_info = request_info;
        this.quantity = quantity;
        this.description = description;
        this.purpose = purpose;
        this.location = location;
        this.requested_by = requested_by;
        this.priority = priority;
        this.date_of_use = date_of_use;
        this.date_created = date_created;
    }

    public int getRequest_id() {
        return request_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getRequest_info() {
        return request_info;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getLocation() {
        return location;
    }

    public String getRequested_by() {
        return requested_by;
    }

    public String getPriority() {
        return priority;
    }

    public String getDate_of_use() {
        return date_of_use;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRequest_info(String request_info) {
        this.request_info = request_info;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRequested_by(String requested_by) {
        this.requested_by = requested_by;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDate_of_use(String date_of_use) {
        this.date_of_use = date_of_use;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
    
    
}
