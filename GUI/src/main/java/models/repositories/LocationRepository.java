package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Location> getList(AbstraticUser user) throws SQLException {

        List<Location> cities = new ArrayList<>();

        ResultSet rs = Database.database().getStmt().executeQuery("SELECT DISTINCT city FROM location");

        while (rs.next()){
            Location location = new Location();
            location.withCity(rs.getString("city"));
            cities.add(location);
        }

        return cities;

    }
}
