/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package responses;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface ResponseDAO {
    public Response getResponseByUserId(int response_id);
    public ArrayList<Response> getAllResponses(int user_id);
    public boolean saveResponse(Response response);
    public boolean insertCompletionResponse(int requestId, int responderId, String comment) ;
    public ArrayList<Response> getCompletedResponsesByUserId(int userId);
    public String getLatestStatusByRequestId(int requestId);

}
