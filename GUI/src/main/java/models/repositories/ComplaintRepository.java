package models.repositories;

import models.Database;
import models.enums.ComplaintStatus;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Complaint;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle the interaction with the table complaints in the DB.
 * Implements the interface Repository(Complaint)
 */
public class ComplaintRepository implements Repository<Complaint> {
    /**
     * This method ads a complaint object to DB
     * @param obj
     * @throws SQLException
     */
    @Override
    public void addToDB(Complaint obj) throws SQLException {
        //executing query
        Database.database().getStmt().executeUpdate("INSERT INTO complaints (s_id, c_id, complaint)" +
                " VALUES ('"+obj.getServiceId()+"','"+obj.getCustomerId()+"','"+obj.getComplaint()+"') ;");
    }

    /**
     * This method selects a complain record from db by a given ID.
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Complaint selectObjById(Object id) throws SQLException {
        //executing query
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM complaints" +
                "WHERE complaint_ID = '"+id+"' ;");
        Complaint complaint = null;
        while(rs.next()){// for each line in the result set, create the complaing object
            complaint = new Complaint(rs.getString("complaint_ID"),rs.getString("s_id"),
                    rs.getString("c_id"), ComplaintStatus.valueOf(rs.getString("complaint_status")),rs.getString("complaint"));
        }
        return complaint; //return the complaint itslef
    }

    /**
     * Complaint is not a userRepository class, therefore this method is usless
     * @param email
     * @return
     * @throws SQLException
     */
    @Override
    public String selectIdOfUser(String email) throws SQLException {
        return null;
    }

    /**
     * This mehtod gets a list of complaints in the DB and returns the list.
     * @param user not relevant, it is just there cause it overrides Repository
     * @return all complaints from complaints table
     * @throws SQLException
     */
    @Override
    public List<Complaint> getList(AbstraticUser user) throws SQLException {
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM complaints");
        List<Complaint> complaints = new ArrayList<>(); // declaring list.
        while(rs.next()){
            //filling up list
            complaints.add(new Complaint(rs.getString("complaint_ID"),rs.getString("s_id"),
                    rs.getString("c_id"), ComplaintStatus.valueOf(rs.getString("complaint_status"))
                    ,rs.getString("complaint")));
        }
        return complaints; // returning list
    }

    /**
     * updataing a complaint status by the status passed as parameter.
     * Status is an object but it is acutally a BookingStatus enum that will be casted to string to concatenate in the query
     * @param complaintID
     * @param status
     */
    public void updateComplaint(String complaintID, Object status) {
        try {// executing query
            Database.database().getStmt().executeUpdate("UPDATE complaints SET complaint_status = '"+status+"'" +
                    "WHERE complaint_ID = '"+complaintID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
