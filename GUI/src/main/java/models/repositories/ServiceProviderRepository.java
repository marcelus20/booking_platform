package models.repositories;

import models.entities.Location;
import models.users.AbstractUser;
import models.entities.ServiceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class ServiceProviderRepository extends Database {


    public ServiceProviderRepository() throws SQLException {
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


            closeConnAndStatement();
            } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public List<List> selectAll() {
        List<List> data = new ArrayList<>();
        try{
            initConnAndStatement();
            String query = new StringBuilder("SELECT * FROM service_provider AS s ")
                    .append("JOIN location AS l ON s.s_id = l.s_id ;").toString();
            ResultSet rs = myStmt.executeQuery(query);

            while (rs.next()){
                List<String> line = new ArrayList<>();
                line.add(rs.getString("s_id"));
                //line.add(rs.getString("password"));
                line.add(rs.getString("email"));
                line.add(rs.getString("date_of_account_creation"));
                line.add(rs.getString("phone"));
                line.add(rs.getString("company_full_name"));
                line.add(rs.getString("approved_status"));
                line.add(rs.getString("first_line_address"));
                line.add(rs.getString("eir_code"));
                line.add(rs.getString("city"));
                line.add(rs.getString("second_line_address"));


                data.add(line);
            }
            closeConnAndStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return data;
    }


    @Override
    public AbstractUser login(String email, String password) {
        return null;
    }



    @Override
    public Long getId(AbstractUser user) {
        user = (ServiceProvider) user;
        return user.getId();
    }

    @Override
    public List<List> executeSelectQuery(String query){
        List<List> data = new ArrayList<>();
        try{
            initConnAndStatement();
            ResultSet rs = myStmt.executeQuery(query);

            while (rs.next()){
                List<String> line = new ArrayList<>();
                line.add(rs.getString("s_id"));
                //line.add(rs.getString("password"));
                line.add(rs.getString("email"));
                line.add(rs.getString("date_of_account_creation"));
                line.add(rs.getString("phone"));
                line.add(rs.getString("company_full_name"));
                line.add(rs.getString("approved_status"));
                line.add(rs.getString("first_line_address"));
                line.add(rs.getString("eir_code"));
                line.add(rs.getString("city"));
                line.add(rs.getString("second_line_address"));

                data.add(line);
            }
            closeConnAndStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return data;
    }

    @Override
    public void executeUpdateQuery(String query){
        try{
            initConnAndStatement();

            myStmt.executeUpdate(query);

            closeConnAndStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


}
