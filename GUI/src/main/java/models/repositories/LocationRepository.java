/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */

package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle the interaction of the the object Location to the database Location table.
 * For what has been developed so far in this project, just the getLIst method were implemented in Repository interface
 */
public class LocationRepository implements Repository <Location>{

    @Override
    public void addToDB(Location obj){

    }

    @Override
    public Location selectObjById(Object id){
        return null;
    }

    @Override
    public String selectIdOfUser(String email){
        return null;
    }

    /**
     * This mehtod selects a list of location cities so far registered in database.
     *
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public List<Location> getList(AbstraticUser user) throws SQLException {
        List<Location> cities = new ArrayList<>();// declaring list of locations
        //executing query
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT DISTINCT city FROM location");
        while (rs.next()){
            //populating city attribute of location and adding it to list
            Location location = new Location();
            location.withCity(rs.getString("city"));
            cities.add(location);
        }
        //returning list
        return cities;

    }
}
