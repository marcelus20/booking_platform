package models;

import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
import models.users.AbstraticUser;
import models.utils.Tools;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {

    protected Connection conn;
    protected Statement stmt;


    public Database() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance() ;

        init();
    }

    public void init() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
        stmt = conn.createStatement();
    }

    public void close() throws SQLException {
        conn.close();
        stmt.close();
    }

    public AbstraticUser login(String email, String password) throws SQLException {
        init();
        AbstraticUser user = null;

        String[] tables = {"admin", "customers", "service_provider"};


        for(String table : tables){
            String query  = new StringBuilder().append("SELECT * FROM ").append(table)
                    .append(" WHERE email = ").append("'").append("marcelus20felipe@gmail.com"/*email*/).append("'")
                    .append(" AND password = '").append("646E613EFCFC1317061B1DF9340E3726"/*password*/).append("' ;").toString();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                if(table.equalsIgnoreCase("admin")){
                    user = Tools.adminMapper(rs);
                }else if (table.equalsIgnoreCase("customers")){
                    user = Tools.customerMapper(rs);
                }else {
                    user = Tools.serviceProviderMapper(rs);
                }
            }
        }
        close();
        return user;
    }

    public List<String>getListOfCities() throws SQLException {
        init();
        List<String> cities = new ArrayList<>();

        ResultSet rs = stmt.executeQuery("SELECT DISTINCT city FROM location");

        while (rs.next()){
            cities.add(rs.getString("city"));
        }
        close();
        return cities;
    }

    public List<List<String>>getListOfBarbersByCity(String city) throws SQLException {
        init();
        List<List<String>> barbers = new ArrayList<>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM service_provider AS b JOIN location AS l ON b.s_id = l.s_id;");

        while (rs.next()){
            List<String> line = new ArrayList<>();
            line.add(rs.getString("s_id"));
            line.add(rs.getString("email"));
            line.add(rs.getString("phone"));
            line.add(rs.getString("company_full_name"));
            line.add(rs.getString("first_line_address"));
            line.add(rs.getString("city"));
            barbers.add(line);
        }
        close();
        return barbers;
    }

    public List<Tuple<TupleOf3Elements<String, String, String>,List<String>>> getShortenedListOfBookings(String customerId) throws SQLException {


        List<Tuple<TupleOf3Elements<String, String, String>,List<String>>> result = new ArrayList<>();


        init();
        ResultSet rs = stmt.executeQuery("SELECT b.time_stamp, b.s_id, b.customer_id ,s.company_full_name "+
                "FROM booking b JOIN service_provider s ON b.s_id = s.s_id WHERE b.customer_id = " + customerId + ";");

        while(rs.next()){
            List<String> line = new ArrayList<>();
            TupleOf3Elements<String, String, String> id = TupleOf3Elements
                    .tupleOf3Elements(rs.getString("time_stamp") , rs.getString("customer_id"), rs.getString("s_id"));

           line.add(rs.getString("time_stamp"));
           line.add(rs.getString("company_full_name"));

           result.add(Tuple.tuple(id, line));
        }
        close();
        return result;
    }

}
