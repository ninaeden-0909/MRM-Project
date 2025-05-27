/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package all_requests;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface RequestDAO {
    public boolean create(Request request);
    public Request read_one(int id);
    public ArrayList<Request> read_all();
}
