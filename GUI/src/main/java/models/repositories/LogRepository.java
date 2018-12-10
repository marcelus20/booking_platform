package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle operation writing, reading and updating in database
 * in the Log table in database
 */
public class LogRepository implements Repository<Log> {
    /**
     * Adding a new record to the table log
     * @param obj
     * @throws SQLException
     */
    @Override
    public void addToDB(Log obj) throws SQLException {
        //executing query
        Database.database().getStmt().executeUpdate("INSERT INTO logs (id, activity_log)" +
                " VALUES ('"+obj.getUserId()+"', '"+obj.getActivityLog()+"');");
    }

    /**
     * Returns the log with a given ID
     * @param id
     * @return
     * @throws SQLException
     */
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

    /**
     * Does not apply for logs table
     * @param email
     * @return
     */

    @Override
    public String selectIdOfUser(String email) {
        return null;
    }

    /**
     * Returns list of logs regardless of owner.
     * @param user - is not relevant, it is just there cause it overrides Repository method.
     * @return
     * @throws SQLException
     */

    @Override
    public List<Log> getList(AbstraticUser user) throws SQLException {
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM logs");
        List<Log> logs = new ArrayList<>();
        while (rs.next()){
            logs.add(new Log(rs.getString("id"), rs.getString("activity_log")));
        }
        //return list of logs
        return logs;
    }
}
