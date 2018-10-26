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

public class ServiceProviderSignUpFormRepository extends Database {


    public ServiceProviderSignUpFormRepository() throws SQLException {
    }

    @Override
    public void insertData(Object obj) {
        ServiceProvider user = (ServiceProvider) obj;
        Location location = user.getLocations().get(0);

        try{
            //Here is where it will look in all tables to see if it finds a user

            String query = new StringBuilder()
                    .append("INSERT INTO service_provider ")
                    .append("(phone, password, email, date_of_account_creation, company_full_name, approved_status) ")
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
                    .append(", ")
                    .append("'").append(user.getApprovedStatus()).append("'")
                    .append(");")
                    .toString();

            System.out.println(query);

            myStmt.executeUpdate(query);

            query = new StringBuilder().append("SELECT * FROM service_provider ")
                    .append("WHERE ")
                    .append("company_full_name = ")
                    .append("'").append(user.getCompanyFullName()).append("'")
                    .append(" AND ")
                    .append(" email = ")
                    .append("'").append(user.geteMail()).append("'")
                    .append(";").toString();
            System.out.println(query);

            Long id = 0L;

            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()){
                id = Long.parseLong(rs.getString("s_id"));
            }

            query = new StringBuilder().append("INSERT INTO location ")
                    .append("(s_id, first_line_address, eir_code, city, second_line_address) ")
                    .append(" VALUES (")
                    .append("'").append(id).append("'")
                    .append(", ")
                    .append("'").append(location.getFirst_line_address()).append("'")
                    .append(", ")
                    .append("'").append(location.getEir_code()).append("'")
                    .append(", ")
                    .append("'").append(location.getCity()).append("'")
                    .append(", ")
                    .append("'").append(location.getSecond_line_address()).append("'")
                    .append(");")
                    .toString();

            System.out.println(query);

            myStmt.executeUpdate(query);



            } catch (SQLException e1) {
            e1.printStackTrace();
        }




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

    @Override
    public Long getId(AbstractUser user) {
        user = (ServiceProvider) user;
        return user.getId();
    }


}
