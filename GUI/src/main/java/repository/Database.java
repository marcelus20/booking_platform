package repository;

import interfaces.Repository;
import models.users.AbstractUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class Database implements Repository {

    protected static Connection myConn;
    protected static Statement myStmt;

    public Database() throws SQLException {
         myConn = DriverManager
                .getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
         myStmt = myConn.createStatement();
    }

    @Override
    public void insertData(Object obj) {

    }

    @Override
    public List<List> selectData(String query) {
        return null;
    }

    @Override
    public AbstractUser login(String email, String password) {
        return null;
    }

    @Override
    public void updateData() {

    }

}
