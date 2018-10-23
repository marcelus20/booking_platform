package repository;

import interfaces.Repository;
import models.users.AbstractUser;
import models.users.Admin;
import models.users.Customer;
import models.users.ServiceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginRepository implements Repository {



    @Override
    public void insertData(Object obj) {

    }

    @Override
    public List<List> selectData(String query) {
        return null;
    }

    @Override
    public AbstractUser selectObj(String email, String password) {
        List<List> result= new ArrayList<>();
        AbstractUser user;
        Map<Integer, AbstractUser> tableMapper = new HashMap<>();;
        Integer n = 0;
        try{
            Connection myConn = DriverManager
                    .getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
            Statement myStmt = myConn.createStatement();
            //Here is where it will look in all tables to see if it finds a user
            String query = "";
            ResultSet rs;
            String[] tableTypes = {"admin", "customers", "service_provider"};
            Boolean empty = true;



            while(empty){
                query = new StringBuilder()
                        .append("SELECT * FROM ")
                        .append(tableTypes[n])
                        .append(" WHERE email = '")
                        .append(email)
                        .append("' && password='")
                        .append(password)
                        .append("';").toString();

                rs = myStmt.executeQuery(query);
                //checking if that's empty
                if(rs.next()){
                    empty = false;
                }
                if(n == 2){
                    empty = false;
                }
                n++;
            }


            tableMapper.put(1, new Admin());
            tableMapper.put(2, new Customer());
            tableMapper.put(3, new ServiceProvider());

            rs = myStmt.executeQuery(query);

            user = tableMapper.get(n);
            while (rs.next()){

                if(user instanceof Admin){
                    generateLineOfAdmin(result, rs);
                }else if(user instanceof Customer){
                    generateLineOfCustomer(result, rs);
                }else if (user instanceof ServiceProvider){
                    generateLineOfServiceProvider(result, rs);
                }
                /*
                System.out.println(rs.getString("password"));
                System.out.println(rs.getString("eMail"));
                */
            }


            myStmt.close();
            myConn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(result.size() == 0){
            return null;
        }else{
            System.out.println(result);
            user = mapLineToUser(n, tableMapper, result.get(0));
            return user;
        }

    }

    @Override
    public void updateData() {

    }

    private void generateLineOfAdmin(List<List> result, ResultSet rs) throws SQLException {
        List<String> line = new ArrayList<>();
        line.add(rs.getString("id"));
        line.add(rs.getString("email"));
        line.add(rs.getString("password"));
        result.add(line);
    }
    private void generateLineOfCustomer(List<List> result, ResultSet rs) throws SQLException {
        List<String> line = new ArrayList<>();
        line.add(rs.getString("customer_id"));
        line.add(rs.getString("email"));
        line.add(rs.getString("password"));
        line.add(rs.getString("phone"));
        line.add(rs.getString("date_of_account_creation"));
        line.add(rs.getString("first_name"));
        line.add(rs.getString("last_name"));
        result.add(line);
    }
    private void generateLineOfServiceProvider(List<List> result, ResultSet rs) throws SQLException {

        List<String> line = new ArrayList<>();
        line.add(rs.getString("s_id"));
        line.add(rs.getString("password"));
        line.add(rs.getString("email"));
        line.add(rs.getString("phone"));
        line.add(rs.getString("date_of_account_creation"));
        line.add(rs.getString("company_full_name"));
        result.add(line);
    }

    private AbstractUser mapLineToUser(Integer tableKey, Map<Integer,
            AbstractUser> tableMapper, List<String> mappedLine){

        if(tableMapper.get(tableKey) instanceof Admin){
            Admin admin = new Admin();
            admin.withId(Long.valueOf(mappedLine.get(0)));
            admin.withEmail(mappedLine.get(1));
            admin.withPassword(mappedLine.get(2));

            return admin;
        }else if(tableMapper.get(tableKey) instanceof Customer){
            Customer customer = new Customer();
            customer.withId(Long.valueOf(mappedLine.get(0)));
            customer.withEmail(mappedLine.get(1));
            customer.withPassword(mappedLine.get(2));
            customer.withPhone(mappedLine.get(3));
            //customer.withDateOfAccountCreation(Date.parse(mappedLine.get(4)));
            customer.withFirstName(mappedLine.get(5));
            customer.withLastName(mappedLine.get(6));
            return customer;
        }else if (tableMapper.get(tableKey) instanceof ServiceProvider){

            ServiceProvider svp = new ServiceProvider();
            svp.withId(Long.valueOf(mappedLine.get(0)));
            svp.withPassword(mappedLine.get(1));
            svp.withEmail(mappedLine.get(2));
            svp.withPhone(mappedLine.get(3));
            //svp.withDateOfAccountCreation(Date.parse(mappedLine.get(4)));
            svp.withCompanyFullName(mappedLine.get(5));
            return svp;
        }else{return null;}
    }


}
