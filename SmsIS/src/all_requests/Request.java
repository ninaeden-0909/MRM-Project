/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package all_requests;

/**
 *
 * @author Lenovo
 */
public class Request {
    private int request_id;
    private int user_id;
    private String request_info;
    private String request_type;
    private String quantity;
    private String description;
    private String purpose;
    private String requested_by;
    private String location;
    private String priority;
    private String date_created;

    // Add constructors, getters, and setters

    public Request(int request_id, int user_id, String request_info, String request_type, String quantity, String description, String purpose, String requested_by, String location, String priority, String date_created) {
        this.request_id = request_id;
        this.user_id = user_id;
        this.request_info = request_info;
        this.request_type = request_type;
        this.quantity = quantity;
        this.description = description;
        this.purpose = purpose;
        this.requested_by = requested_by;
        this.location = location;
        this.priority = priority;
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

    public String getRequest_type() {
        return request_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getRequested_by() {
        return requested_by;
    }

    public String getLocation() {
        return location;
    }

    public String getPriority() {
        return priority;
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

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setRequested_by(String requested_by) {
        this.requested_by = requested_by;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
    
}
