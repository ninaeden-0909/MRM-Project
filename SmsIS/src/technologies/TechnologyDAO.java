/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package technologies;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface TechnologyDAO {
    public boolean create(Technology technology);
    public Technology read_one(int technologySupply_id);
    public ArrayList<Technology> read_all();
    public boolean update(int technologySupply_id, Technology technology);
    public boolean delete(int technologySupply_id);
    public ArrayList<Technology> search(String str);
}