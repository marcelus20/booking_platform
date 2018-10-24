package repository;

import interfaces.Repository;
import models.users.Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AdminSignUpFormRepository implements Repository {

    @Override
    public void insertData(Object obj) {
        Admin user = (Admin) obj;
        try{
            Connection myConn = DriverManager
                    .getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
            Statement myStmt = myConn.createStatement();
            //Here is where it will look in all tables to see if it finds a user

            String queryInsertionToServiceProviderTable = new StringBuilder()
                    .append("INSERT INTO admin ")
                    .append("(password, email) ")
                    .append("VALUES( ")
                    .append("'").append(user.getPassword()).append("'")
                    .append(", ")
                    .append("'").append(user.geteMail()).append("'")
                    .append(");")
                    .toString();

            System.out.println(queryInsertionToServiceProviderTable);


            myStmt.executeUpdate(queryInsertionToServiceProviderTable);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public List<List> selectData(String query) {
        return null;
    }

    @Override
    public Object selectObj(String email, String password) {
        return null;
    }

    @Override
    public void updateData() {

    }
}
