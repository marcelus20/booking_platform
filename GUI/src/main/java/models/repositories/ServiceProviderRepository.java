package models.repositories;

import models.Database;
import models.enums.ServiceProviderStatus;
import models.tuples.entitiesRepresentation.*;
import models.tuples.joinedEntities.ServiceProviderTableView;
import models.utils.Tools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This mehtod handles the interaction of the Service provider with the service_provider table schema in the database
 */
public class ServiceProviderRepository implements Repository<ServiceProvider>{

    /**
     * empty constructor
     */
    public ServiceProviderRepository() {

    }

    /**
     * Addds a service provider to DB.
     * Records firstly in users table, then records the service_provider table, then records the phone_list table and
     * then records in the location table
     * @param obj
     * @throws SQLException
     */
    @Override
    public void addToDB(ServiceProvider obj) throws SQLException {
        ServiceProvider s = obj;
        Location l = s.getLocation();
        s.withPassword(Tools.hashingPassword(s.getPassword()));
        //FOR THE NEW service provider
        String query = new StringBuilder()//* preparing first query: insertig into users
                .append("INSERT INTO users (user_type, email, password, date_created) VALUES ( ")
                .append("'").append(s.getUserType()).append("', ")
                .append("'").append(s.getEmail()).append("', ")
                .append("'").append(s.getPassword()).append("', ")
                .append("'").append(s.getDateCreated()).append("'")
                .append(")").toString();

        Database.database().getStmt().executeUpdate(query); // executing query
        String id = selectIdOfUser(s.getEmail());
        System.out.println(id);
        //preparing second query -> inserting into phone_list
        query = "INSERT INTO phone_list (id, phone) VALUES ('"+id+"','"+s.getPhone().getPhone()+"')";
        Database.database().getStmt().executeUpdate(query);//executing query
        //preparing third query -> inserting into service provider
        query = "INSERT  INTO service_provider (s_id, company_full_name, approved_status) " +
                "VALUES ('"+id+"','"+s.getCompanyFullName()+"','"+s.getStatus()+"')";
        Database.database().getStmt().executeUpdate(query);
        //FOR THE LOCATION WISE
        query = new StringBuilder()
                .append("INSERT INTO location (s_id, first_line_address, eir_code,")
                .append(" city, second_line_address) VALUES ( ")
                .append("'").append(id).append("', ")// mapping id
                .append("'").append(l.getFirstLineAddress()).append("', ")// mapping first line address
                .append("'").append(l.getEirCode()).append("', ") // mapping eircode
                .append("'").append(l.getCity()).append("', ")// mapping city
                .append("'").append(l.getSecondLineAddress()).append("'); ")// mapping second line address
                .toString();

        /**
         * executing query -> inserting location into database
         */
        Database.database().getStmt().executeUpdate(query);
    }

    /**
     * selects a service provider from DB and returns the full populated service provider object.
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public ServiceProvider selectObjById(Object id) throws SQLException {
        ServiceProvider serviceProvider = new ServiceProvider(); // declaring empty service provider
        Location location = new Location(); // declating empty location
        List<BookingSlots> bookingSlots = new ArrayList<>(); // declarting list of bookingSlots
        /**
         * The objects above will be populated as the sql queries go.
         */
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM service_provider s JOIN location l ON s.s_id = l.s_id WHERE s.s_id = "+ id +";");

        /**
         * mapping location object
         */
        while(rs.next()){
            serviceProvider.withId((String)id); serviceProvider.withEmail(rs.getString("company_full_name"));
            serviceProvider.withCompanyFullName(rs.getString("company_full_name"));
            serviceProvider.withServiceProviderStatus(ServiceProviderStatus.valueOf(rs.getString("approved_status").toUpperCase()));
            location.withFirstLineAddress(rs.getString("first_line_address"));
            location.withEirCode(rs.getString("eir_code"));
            location.withCity(rs.getString("city")); location.withSecondLineAddress(rs.getString("second_line_address"));

        }
        //populating the location attribute
        serviceProvider.withLocation(location);

        /**
         * mapping the list of bookings object
         */
        rs = Database.database().getStmt().executeQuery("SELECT * FROM service_provider s JOIN booking_slots b ON s.s_id = b.s_id WHERE s.s_id = "+id+";");
        while (rs.next()){
            BookingSlots b = new BookingSlots();
            b.withTimestamp(rs.getTimestamp("timestamp"));
            b.withServiceId((String)id);
            b.withAvailability(rs.getBoolean("availability"));
            bookingSlots.add(b);
        }
        //populating service provider with its booking slots
        serviceProvider.withBookingSlots(bookingSlots);

        //return the the well populated serviceProvider
        return serviceProvider;
    }

    /**
     * returns the id of serviceby a given email
     * @param email
     * @return
     * @throws SQLException
     */
    @Override
    public String selectIdOfUser(String email) throws SQLException {
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT id FROM users WHERE email = '"+email+"'");
        while (rs.next()){
            return rs.getString("id");
        }
        return null;
    }

    @Override
    public List<ServiceProvider> getList(AbstraticUser user) {
        return null;
    }

    /**
     * return a list of service Providers by their status - either pendent, approved or rejected enum values.
     * @param status
     * @return
     * @throws SQLException
     */
    public List<ServiceProvider> selectListOfServiceProvidersByStatus(ServiceProviderStatus status) throws SQLException {
        //declating the list
        List <ServiceProvider> listOfProviders = new ArrayList<>();
        String statusSentence = "";
        //status can be null, in this casse the statusSentence will not concatenate, therefore it will return every status
        if(status != null){
            statusSentence = " WHERE s.approved_status = '"+status+"' ";
        }
        //preparing and executing query
        String query = "SELECT * FROM users u JOIN location l ON u.id = l.s_id JOIN phone_list p ON u.id = p.id " +
                "JOIN service_provider s ON s.s_id = u.id "+statusSentence+" ;";
        System.out.println(query);

        ResultSet rs = Database.database().getStmt().executeQuery(query);
        /**
         * mapping each column of the result set to the service provider attribute
         */
        while (rs.next()){
            ServiceProvider serviceProvider = new ServiceProvider();
            serviceProvider.withId(rs.getString("s_id")); // mapping id column
            serviceProvider.withEmail(rs.getString("email")); // mapping email column
            serviceProvider.withPhone(new Phone(serviceProvider.getId(), rs.getString("phone")));// mapping phone column
            serviceProvider.withCompanyFullName(rs.getString("company_full_name")); // mapping company full name colun
            serviceProvider.withServiceProviderStatus(ServiceProviderStatus
                    .valueOf(rs.getString("approved_status")));// mapping status column

            Location location = new Location();// declaring location that will populate service location attribute
            location.withServiceId(serviceProvider.getId());
            location.withFirstLineAddress(rs.getString("first_line_address")); // mapping first line address
            location.withSecondLineAddress(rs.getString("second_line_address")); // maping second line address column
            location.withCity(rs.getString("city")); // mapping city column
            location.withEirCode(rs.getString("eir_code")); // mapping eir_code column
            serviceProvider.withLocation(location);// assigning location to service provide rattrbiute
            listOfProviders.add(serviceProvider);// adding service provider to the list
        }
        return listOfProviders; // return the list
    }

    /**
     * update a service with the new service provider object with different apprioved status.
     * If the service provider Sarah, for instance is registered in DB, if Sarah from passed by parameter has
     * an APPROVED status whereas in the database Sarah  pending, the Sarah passed as paramenter will overrite the sara
     * in database when it comes to the column status.
     * @param serviceProvider
     * @throws SQLException
     */
    public void updateServiceProviderStatus(ServiceProvider serviceProvider) throws SQLException {

        Database.database().getStmt().executeUpdate("UPDATE service_provider SET approved_status = '"+serviceProvider.getStatus()+"'" +
                " WHERE  s_id = '"+serviceProvider.getId()+"'");

    }

    /**
     * this function return a list of the view Object ServiceProviderTableView by joing entities
     * and populating the object attributes with the resultset columns
     * @param city
     * @return
     */
    public List<ServiceProviderTableView> getListOfServicesTableView(String city) {
        try {
            // preparing query
            String query = "SELECT u.id, u.email, s.company_full_name, l.first_line_address, l.city, p.phone " +
                    "FROM users u JOIN service_provider s ON u.id = s.s_id JOIN location l ON u.id = l.s_id  " +
                    " JOIN phone_list p ON u.id = p.id WHERE l.city = '"+city+"';";
            ResultSet rs;
            //executing query
            rs = Database.database().getStmt().executeQuery(query);
            List<ServiceProviderTableView> tableListOfBarbers= new ArrayList<>();
            //mapping the result set columns to ServiceProviderTableView object
            while (rs.next()){
                ServiceProviderTableView serviceProviderTableView = new ServiceProviderTableView();
                serviceProviderTableView.setServiceId(rs.getString("id"));
                serviceProviderTableView.setServiceEmail(rs.getString("email"));
                serviceProviderTableView.setServiceName(rs.getString("company_full_name"));
                serviceProviderTableView.setAddress(rs.getString("first_line_address"));
                serviceProviderTableView.setCity(rs.getString("city"));
                serviceProviderTableView.setPhone(rs.getString("phone"));
                tableListOfBarbers.add(serviceProviderTableView); // adding to DB
            }
            System.out.println(tableListOfBarbers);
            return tableListOfBarbers; // returning the list.
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
