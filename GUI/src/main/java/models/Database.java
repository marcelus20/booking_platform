package models;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import models.enums.ServiceProviderStatus;
import models.enums.UserType;
import models.repositories.CustomerRepository;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
import models.tuples.entitiesRepresentation.*;
import models.tuples.joinedEntities.ManageBookingView;
import models.tuples.joinedEntities.ServiceProviderTableView;


import java.sql.*;
import java.util.ArrayList;
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

        String query  = new StringBuilder().append("SELECT * FROM ").append("users")
                .append(" WHERE email = ").append("'").append(email).append("'")
                .append(" AND password = '").append(password).append("' ;").toString();

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()){
            System.out.println(rs.getString("user_type"));
             if(rs.getString("user_type").equalsIgnoreCase("ADMIN")){
                 user = new Admin();
                 user.withUserType(UserType.ADMIN);
             }else if (rs.getString("user_type").equalsIgnoreCase("SERVICE_PROVIDER")){
                 user = new ServiceProvider();
                 user.withUserType(UserType.SERVICE_PROVIDER);
             }else{
                 user = new Customer();
                 user.withUserType(UserType.CUSTOMER);
             }

             user.withId(rs.getString("id"));
             user.withEmail(rs.getString("email"));
             user.withPassword(rs.getString("password"));
             user.withDateCreated(rs.getDate("date_created"));
        }

        if(!user.getUserType().equals(UserType.ADMIN)){
            user = populateTheRestofAttributes(user);
        }

        close();
        return user;
    }

    private AbstraticUser populateTheRestofAttributes(AbstraticUser user) throws SQLException {
        String query = "";
        if(user.getUserType().equals(UserType.CUSTOMER)){
            query = "SELECT * FROM customers WHERE c_id = '"+user.getId()+"'";
        }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
            query = "SELECT * FROM service_provider WHERE s_id = '"+user.getId()+"'";
        }
        init();

        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            if(user.getUserType().equals(UserType.CUSTOMER)){
                ((Customer) user).withFirstName(rs.getString("first_name"));
                ((Customer) user).withLastName(rs.getString("last_name"));
            }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
                ((ServiceProvider) user).withCompanyFullName(rs.getString("company_full_name"));
                ((ServiceProvider) user).withServiceProviderStatus(ServiceProviderStatus
                        .valueOf(rs.getString("approved_status")));
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

    public List<ServiceProviderTableView>getListOfBarbersByCity(String city) throws SQLException {
        init();
//        List<List<String>> barbers = new ArrayList<>();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM service_provider AS b JOIN location AS l ON b.s_id = l.s_id;");
        ResultSet rs = stmt.executeQuery("SELECT u.id, u.email, s.company_full_name, l.first_line_address, l.city, p.phone " +
                "FROM users u JOIN service_provider s ON u.id = s.s_id JOIN location l ON u.id = l.s_id  " +
                " JOIN phone_list p ON u.id = p.id WHERE l.city = '"+city+"';");
        List<ServiceProviderTableView> tableListOfBarbers= new ArrayList<>();



        while (rs.next()){
            ServiceProviderTableView serviceProviderTableView = new ServiceProviderTableView();
            serviceProviderTableView.setServiceId(rs.getString("id"));
            serviceProviderTableView.setServiceEmail(rs.getString("email"));
            serviceProviderTableView.setServiceName(rs.getString("company_full_name"));
            serviceProviderTableView.setAddress(rs.getString("first_line_address"));
            serviceProviderTableView.setCity(rs.getString("city"));
            serviceProviderTableView.setPhone(rs.getString("phone"));
            tableListOfBarbers.add(serviceProviderTableView);
        }
        System.out.println(tableListOfBarbers);
        close();
        return tableListOfBarbers;
    }

//    public List<Tuple<TupleOf3Elements<String, String, String>,List<String>>> getShortenedListOfBookings(String customerId) throws SQLException {
//
//
//        List<Tuple<TupleOf3Elements<String, String, String>,List<String>>> result = new ArrayList<>();
//
//
//        init();
//        ResultSet rs = stmt.executeQuery("SELECT b.time_stamp, b.s_id, b.customer_id ,s.company_full_name "+
//                "FROM booking b JOIN service_provider s ON b.s_id = s.s_id WHERE b.customer_id = " + customerId + ";");
//
//        while(rs.next()){
//            List<String> line = new ArrayList<>();
//            TupleOf3Elements<String, String, String> id = TupleOf3Elements
//                    .tupleOf3Elements(rs.getString("time_stamp") , rs.getString("customer_id"), rs.getString("s_id"));
//
//           line.add(rs.getString("time_stamp"));
//           line.add(rs.getString("company_full_name"));
//
//           result.add(Tuple.tuple(id, line));
//        }
//        close();
//        return result;
//    }

    public List<ManageBookingView> generateBookingView(AbstraticUser user, BookingStatus bookingStatus) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<ManageBookingView> manageBookingViews = new ArrayList<>();
        Repository<ServiceProvider> serviceProviderRepository = new ServiceProviderRepository();
        Repository<Customer> customerRepository = new CustomerRepository();



        init();
        String userString = "";
        if(user.getUserType().equals(UserType.CUSTOMER)){
            userString = " b.c_id ";
        }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
            userString = " b.s_id ";
        }

        String bookingStatusSentence = "";
        if(bookingStatus != null){
            bookingStatusSentence += " AND booking_status = '"+bookingStatus+"' ";
        }
        String query = "select * from booking b join service_provider s on b.s_id = s.s_id" +
                " join phone_list p on p.id = b.s_id where "+userString+"  = "+user.getId()+" " +
                bookingStatusSentence+" ;";

        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()){
            ManageBookingView manageBookingView = new ManageBookingView();
            manageBookingView.setServiceProvider(serviceProviderRepository.selectObjById(rs.getString("s_id")));
            manageBookingView.setCustomer(customerRepository.selectObjById(rs.getString("c_id")));
            manageBookingView.setTimestamp(rs.getTimestamp("time_stamp"));
            manageBookingView.setServiceId(rs.getString("s_id"));
            manageBookingView.setCustomerId(rs.getString("c_id"));
            manageBookingView.setReview(BookingReview.valueOf(rs.getString("review")));
            manageBookingView.setCompanyName(rs.getString("company_full_name"));
            manageBookingView.setBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));
            manageBookingView.setPhone(rs.getString("phone"));

            manageBookingViews.add(manageBookingView);
        }

        close();

        return manageBookingViews;
    }
}
