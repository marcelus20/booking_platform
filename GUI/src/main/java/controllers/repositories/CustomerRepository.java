package controllers.repositories;

import models.users.AbstractUser;
import models.users.Customer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository extends Database{


    public CustomerRepository() throws SQLException {
    }

    @Override
    public void insertData(final Object customer) {
        final Customer user = (Customer) customer;

        try{
            //Here is where it will look in all tables to see if it finds a user

            String queryInsertionToServiceProviderTable = new StringBuilder()
                    .append("INSERT INTO customers ")
                    .append("(password, email, phone, first_name, last_name, date_of_account_creation) ")
                    .append("VALUES( ")
                    .append("'").append(user.getPassword()).append("'")
                    .append(", ")
                    .append("'").append(user.geteMail()).append("'")
                    .append(", ")
                    .append("'").append(user.getPhone()).append("'")
                    .append(", ")
                    .append("'").append(user.getFirstName()).append("'")
                    .append(", ")
                    .append("'").append(user.getLastName()).append("'")
                    .append(",")
                    .append("'").append(new Date(System.currentTimeMillis())).append("'")
                    .append(");")
                    .toString();

            System.out.println(queryInsertionToServiceProviderTable);

            myStmt.executeUpdate(queryInsertionToServiceProviderTable);
            closeConnAndStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public List<List> selectAll() {
        List<List> data = new ArrayList<>();
        try{
            ResultSet rs = myStmt.executeQuery("SELECT * FROM customers;");

            while (rs.next()){
                List<String> line = new ArrayList<>();
                line.add(rs.getString("customer_id"));
                //line.add(rs.getString("password"));
                line.add(rs.getString("email"));
                line.add(rs.getString("phone"));
                line.add(rs.getString("first_name"));
                line.add(rs.getString("last_name"));
                line.add(rs.getString("date_of_account_creation"));


                data.add(line);
            }
            closeConnAndStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return data;

    }

    @Override
    public List<List> executeSelectQuery(String query) {
        return null;
    }

    @Override
    public Long getId(AbstractUser user) {
        return null;
    }


}
