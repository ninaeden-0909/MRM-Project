/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Lenovo
 */
public class Service {
    private int request_id;
    private int user_id;
    private String request_info;
    private String description;
    private String location;
    private String priority;
    private String date_created;

    public Service(int request_id, int user_id, String request_info, String description, String location, String priority, String date_created) {
        this.request_id = request_id;
        this.user_id = user_id;
        this.request_info = request_info;
        this.description = description;
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

    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        this.description = description;
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
