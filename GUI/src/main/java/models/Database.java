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



    public static Database database(){
        if(_database == null){
            _database = new Database();
        }
        return _database;
    }

    //connection
    private Connection conn;


    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance() ;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
//            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Statement getStmt() {
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
