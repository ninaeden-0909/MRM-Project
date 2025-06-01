/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responses;

/**
 *
 * @author Lenovo
 */
public class Response {
    private int response_id;
    private int request_id;
    private int responder_id;
    private String comment;
    private String status_update;
    private String schedule_Datetime;
    private String date_responded;

    public Response(int response_id, int request_id, int responder_id, String comment, String status_update, String schedule_Datetime, String date_responded) {
        this.response_id = response_id;
        this.request_id = request_id;
        this.responder_id = responder_id;
        this.comment = comment;
        this.status_update = status_update;
        this.schedule_Datetime = schedule_Datetime;
        this.date_responded = date_responded;
    }

    public int getResponse_id() {
        return response_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public int getResponder_id() {
        return responder_id;
    }

    public String getComment() {
        return comment;
    }

    public String getStatus_update() {
        return status_update;
    }

    public String getSchedule_Datetime() {
        return schedule_Datetime;
    }

    public String getDate_responded() {
        return date_responded;
    }

    public void setResponse_id(int response_id) {
        this.response_id = response_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public void setResponder_id(int responder_id) {
        this.responder_id = responder_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStatus_update(String status_update) {
        this.status_update = status_update;
    }

    public void setSchedule_Datetime(String schedule_Datetime) {
        this.schedule_Datetime = schedule_Datetime;
    }

    public void setDate_responded(String date_responded) {
        this.date_responded = date_responded;
    }

    
}