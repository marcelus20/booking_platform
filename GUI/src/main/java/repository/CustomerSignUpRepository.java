package repository;

import interfaces.Repository;
import models.Location;
import models.users.AbstractUser;
import models.users.Customer;
import models.users.ServiceProvider;


import java.sql.*;
import java.util.List;

public class CustomerSignUpRepository extends Database{


    public CustomerSignUpRepository() throws SQLException {
    }

    @Override
    public void insertData(final Object obj) {
        final Customer user = (Customer) obj;

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
                    .append("'").append(user.getFirstName()).append("'")
                    .append(", ")
                    .append("'").append(user.getPhone()).append("'")
                    .append(", ")
                    .append("'").append(user.getLastName()).append("'")
                    .append(",")
                    .append("'").append(new Date(System.currentTimeMillis())).append("'")
                    .append(");")
                    .toString();

            System.out.println(queryInsertionToServiceProviderTable);

            myStmt.executeUpdate(queryInsertionToServiceProviderTable);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public Long getId(AbstractUser user) {
        return null;
    }


}
