package repository;

import interfaces.Repository;
import models.Location;
import models.users.AbstractUser;
import models.users.Admin;
import models.users.Customer;
import models.users.ServiceProvider;
import views.signUpForms.ServiceProviderSignUpForm;

import java.sql.*;
import java.util.List;
import java.sql.Date;

public class ServiceProviderSignUpFormRepository implements Repository {


    @Override
    public void insertData(Object obj) {
        ServiceProvider user = (ServiceProvider) obj;
        Location location = user.getLocations().get(0);

        try{
            Connection myConn = DriverManager
                    .getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
            Statement myStmt = myConn.createStatement();
            //Here is where it will look in all tables to see if it finds a user

            String queryInsertionToServiceProviderTable = new StringBuilder()
                    .append("INSERT INTO service_provider ")
                    .append("(phone, password, email, date_of_account_creation, company_full_name) ")
                    .append("VALUES( ")
                    .append("'").append(user.getPhone()).append("'")
                    .append(", ")
                    .append("'").append(user.getPassword()).append("'")
                    .append(", ")
                    .append("'").append(user.geteMail()).append("'")
                    .append(", ")
                    .append("'").append(new Date(System.currentTimeMillis())).append("'")
                    .append(", ")
                    .append("'").append(user.getCompanyFullName()).append("'")
                    .append(");")
                    .toString();

            System.out.println(queryInsertionToServiceProviderTable);



            /*String queryInsertionToLocationTable = new StringBuilder()
                    .append("INSERT INTO location")
                    .append("( s_id, first_line_address, eir_code, city, second_line_address)")
                    .append("VALUES( ")
                    .append(location.get)*/
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
