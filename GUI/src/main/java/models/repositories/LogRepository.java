package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogRepository implements Repository<Log> {
    @Override
    public void addToDB(Log obj) throws SQLException {
        Database.database().getStmt().executeUpdate("INSERT INTO logs (id, activity_log)" +
                " VALUES ('"+obj.getUserId()+"', '"+obj.getActivityLog()+"');");
    }

    @Override
    public Log selectObjById(Object id) throws SQLException {
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM logs" +
                " WHERE id = '"+id+"' ;");
        Log log = null;
        while (rs.next()){
            log = new Log(rs.getString("id"), rs.getString("activity_log"));
        }

        return log;
    }

    @Override
    public String selectIdOfUser(String email) {
        return null;
    }

    @Override
    public List<Log> getList(AbstraticUser user) throws SQLException {
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM logs");
        List<Log> logs = new ArrayList<>();
        while (rs.next()){
            logs.add(new Log(rs.getString("id"), rs.getString("activity_log")));
        }

        return logs;
    }
}
