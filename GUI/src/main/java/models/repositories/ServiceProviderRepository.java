package models.repositories;

import models.Database;
import models.enums.ServiceProviderStatus;
import models.tuples.entitiesRepresentation.*;
import models.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderRepository implements Repository<ServiceProvider>{


    public ServiceProviderRepository() {

    }

    @Override
    public void addToDB(ServiceProvider obj) throws SQLException {

        ServiceProvider s = obj;
        Location l = s.getLocation();

        s.withPassword(Tools.hashingPassword(s.getPassword()));

        //FOR THE NEW service provider
        String query = new StringBuilder()
                .append("INSERT INTO users (user_type, email, password, date_created) VALUES ( ")
                .append("'").append(s.getUserType()).append("', ")
                .append("'").append(s.getEmail()).append("', ")
                .append("'").append(s.getPassword()).append("', ")
                .append("'").append(s.getDateCreated()).append("'")
                .append(")").toString();


        Database.database().getStmt().executeUpdate(query);

        String id = selectIdOfUser(s.getEmail());
        System.out.println(id);

        query = "INSERT INTO phone_list (id, phone) VALUES ('"+id+"','"+s.getPhone().getPhone()+"')";

        Database.database().getStmt().executeUpdate(query);

        query = "INSERT  INTO service_provider (s_id, company_full_name, approved_status) " +
                "VALUES ('"+id+"','"+s.getCompanyFullName()+"','"+s.getStatus()+"')";

        Database.database().getStmt().executeUpdate(query);

        //FOR THE LOCATION WISE
        query = new StringBuilder()
                .append("INSERT INTO location (s_id, first_line_address, eir_code,")
                .append(" city, second_line_address) VALUES ( ")
                .append("'").append(id).append("', ")
                .append("'").append(l.getFirstLineAddress()).append("', ")
                .append("'").append(l.getEirCode()).append("', ")
                .append("'").append(l.getCity()).append("', ")
                .append("'").append(l.getSecondLineAddress()).append("'); ")
                .toString();

        Database.database().getStmt().executeUpdate(query);

    }

    @Override
    public ServiceProvider selectObjById(Object id) throws SQLException {
        ServiceProvider serviceProvider = new ServiceProvider();
        Location location = new Location();

        List<BookingSlots> bookingSlots = new ArrayList<>();

        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM service_provider s JOIN location l ON s.s_id = l.s_id WHERE s.s_id = "+ id +";");

        while(rs.next()){
            serviceProvider.withId((String)id); serviceProvider.withEmail(rs.getString("company_full_name"));
            serviceProvider.withCompanyFullName(rs.getString("company_full_name"));
            serviceProvider.withServiceProviderStatus(ServiceProviderStatus.valueOf(rs.getString("approved_status").toUpperCase()));
            location.withFirstLineAddress(rs.getString("first_line_address"));
            location.withEirCode(rs.getString("eir_code"));
            location.withCity(rs.getString("city")); location.withSecondLineAddress(rs.getString("second_line_address"));

        }

        serviceProvider.withLocation(location);

        rs = Database.database().getStmt().executeQuery("SELECT * FROM service_provider s JOIN booking_slots b ON s.s_id = b.s_id WHERE s.s_id = "+id+";");
        while (rs.next()){
            BookingSlots b = new BookingSlots();
            b.withTimestamp(rs.getTimestamp("timestamp"));
            b.withServiceId((String)id);
            b.withAvailability(rs.getBoolean("availability"));
            bookingSlots.add(b);
        }

        serviceProvider.withBookingSlots(bookingSlots);

        return serviceProvider;
    }

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

    public List<ServiceProvider> selectListOfServiceProvidersByStatus(ServiceProviderStatus status) throws SQLException {

        List <ServiceProvider> listOfProviders = new ArrayList<>();
        String statusSentence = "";
        if(status != null){
            statusSentence = " WHERE s.approved_status = '"+status+"' ";
        }

        String query = "SELECT * FROM users u JOIN location l ON u.id = l.s_id JOIN phone_list p ON u.id = p.id " +
                "JOIN service_provider s ON s.s_id = u.id "+statusSentence+" ;";
        System.out.println(query);

        ResultSet rs = Database.database().getStmt().executeQuery(query);

        while (rs.next()){
            ServiceProvider serviceProvider = new ServiceProvider();
            serviceProvider.withId(rs.getString("s_id"));
            serviceProvider.withEmail(rs.getString("email"));
            serviceProvider.withPhone(new Phone(serviceProvider.getId(), rs.getString("phone")));
            serviceProvider.withCompanyFullName(rs.getString("company_full_name"));
            serviceProvider.withServiceProviderStatus(ServiceProviderStatus
                    .valueOf(rs.getString("approved_status")));

            Location location = new Location();
            location.withServiceId(serviceProvider.getId());
            location.withFirstLineAddress(rs.getString("first_line_address"));
            location.withSecondLineAddress(rs.getString("second_line_address"));
            location.withCity(rs.getString("city"));
            location.withEirCode(rs.getString("eir_code"));
            serviceProvider.withLocation(location);
            listOfProviders.add(serviceProvider);
        }

        return listOfProviders;
    }

    public void updateServiceProviderStatus(ServiceProvider serviceProvider) throws SQLException {

        Database.database().getStmt().executeUpdate("UPDATE service_provider SET approved_status = '"+serviceProvider.getStatus()+"'" +
                " WHERE  s_id = '"+serviceProvider.getId()+"'");

    }

}
