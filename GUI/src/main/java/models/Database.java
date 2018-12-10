package models;

import java.sql.*;

/**
 * This Singleton class will handle the connection with the database.
 *
 * The stmt attribute will be used outside of the class to execute sql queries.
 */
public class Database {

    /**
     * The unique instance of database class
     */
    private static Database _database = null;


    /**
     * public fatory class for initialising the database instance
     * @return
     */
    public static Database database(){
        if(_database == null){ // if null, call private contructor
            _database = new Database();
        }
        return _database; // return the instance
    }

    //Connection attribute
    private Connection conn;


    /**
     * private Constructor
     */
    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance() ; // calling driver by reflection
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        init();
    }

    /**
     * initialises the conn attribute
     */
    private void init(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
//            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * closes the conn attribute
     */
    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * generates new Statement and returns it.
     * @return
     */
    public Statement getStmt() {
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
