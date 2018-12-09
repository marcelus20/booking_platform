package models.repositories;

import models.Database;
import models.enums.ComplaitStatus;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Complaint;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintRepository implements Repository<Complaint> {
    @Override
    public void addToDB(Complaint obj) throws SQLException {
        Database.database().getStmt().executeUpdate("INSERT INTO complaints (s_id, c_id, complaint)" +
                " VALUES ('"+obj.getServiceId()+"','"+obj.getCustomerId()+"','"+obj.getComplaint()+"') ;");
    }

    @Override
    public Complaint selectObjById(Object id) throws SQLException {
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM complaints" +
                "WHERE complaint_ID = '"+id+"' ;");
        Complaint complaint = null;
        while(rs.next()){
            complaint = new Complaint(rs.getString("complaint_ID"),rs.getString("s_id"),
                    rs.getString("c_id"), ComplaitStatus.valueOf(rs.getString("complaint_status")),rs.getString("complaint"));
        }
        return complaint;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {
        return null;
    }

    @Override
    public List<Complaint> getList(AbstraticUser user) throws SQLException {
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM complaints");
        List<Complaint> complaints = new ArrayList<>();
        while(rs.next()){
            complaints.add(new Complaint(rs.getString("complaint_ID"),rs.getString("s_id"),
                    rs.getString("c_id"), ComplaitStatus.valueOf(rs.getString("complaint_status"))
                    ,rs.getString("complaint")));
        }
        return complaints;
    }

    public void updateComplaint(String complaintID, Object status) {
        try {
            Database.database().getStmt().executeUpdate("UPDATE complaints SET complaint_status = '"+status+"'" +
                    "WHERE complaint_ID = '"+complaintID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
