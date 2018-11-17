package models.repositories;

import models.Database;
import models.enums.ServiceProviderStatus;
import models.tuples.Tuple;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.BookingSlots;
import models.tuples.entitiesRepresentation.Location;
import models.tuples.entitiesRepresentation.ServiceProvider;
import models.utils.Tools;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderRepository extends Database implements Repository<ServiceProvider>{


    public ServiceProviderRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

    }

    @Override
    public void addToDB(ServiceProvider obj) throws SQLException {
        init();
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


        stmt.executeUpdate(query);

        String id = selectIdOfUser(s.getEmail());
        System.out.println(id);

        query = "INSERT INTO phone_list (id, phone) VALUES ('"+id+"','"+s.getPhone().getPhone()+"')";

        stmt.executeUpdate(query);

        query = "INSERT  INTO service_provider (s_id, company_full_name, approved_status) " +
                "VALUES ('"+id+"','"+s.getCompanyFullName()+"','"+s.getStatus()+"')";

        stmt.executeUpdate(query);

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

        stmt.executeUpdate(query);

        close();
    }

    @Override
    public ServiceProvider selectObjById(Object id) throws SQLException {
        ServiceProvider serviceProvider = new ServiceProvider();
        Location location = new Location();

        List<BookingSlots> bookingSlots = new ArrayList<>();

        init();

        ResultSet rs = stmt.executeQuery("SELECT * FROM service_provider s JOIN location l ON s.s_id = l.s_id WHERE s.s_id = "+ id +";");

        while(rs.next()){
            serviceProvider.withId((String)id); serviceProvider.withEmail(rs.getString("company_full_name"));
            serviceProvider.withCompanyFullName(rs.getString("company_full_name"));
            serviceProvider.withServiceProviderStatus(ServiceProviderStatus.valueOf(rs.getString("approved_status").toUpperCase()));
            location.withFirstLineAddress(rs.getString("first_line_address"));
            location.withEirCode(rs.getString("eir_code"));
            location.withCity(rs.getString("city")); location.withSecondLineAddress(rs.getString("second_line_address"));

        }

        serviceProvider.withLocation(location);

        rs = stmt.executeQuery("SELECT * FROM service_provider s JOIN booking_slots b ON s.s_id = b.s_id WHERE s.s_id = "+id+";");
        while (rs.next()){
            BookingSlots b = new BookingSlots();
            b.withTimestamp(rs.getTimestamp("timestamp"));
            b.withServiceId((String)id);
            b.withAvailability(rs.getBoolean("availability"));
            bookingSlots.add(b);
        }

        serviceProvider.withBookingSlots(bookingSlots);

        close();
        return serviceProvider;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {
        init();

        ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE email = '"+email+"'");
        while (rs.next()){
            return rs.getString("id");
        }

        close();
        return null;
    }

    @Override
    public List<ServiceProvider> getList(AbstraticUser user) throws SQLException {
        return null;
    }

    public List<Tuple<String, List<String>>> selectListOfServiceProvidersByStatus(ServiceProviderStatus status) throws SQLException {
        init();

        List <Tuple<String, List<String>>> listOfResults = new ArrayList<>();

        String query = "SELECT s.s_id, s.email, s.phone, s.company_full_name, approved_status," +
                " l.first_line_address, l.second_line_address, l.city FROM service_provider s" +
                " JOIN location l ON s.s_id = l.s_id WHERE s.approved_status = '"+status+"';";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            List<String> line = new ArrayList<>();
            String id = rs.getString("s_id");
            line.add(rs.getString("s_id"));
            line.add(rs.getString("email"));
            line.add(rs.getString("phone"));
            line.add(rs.getString("company_full_name"));
            line.add(rs.getString("approved_status"));
            line.add(rs.getString("first_line_address"));
            line.add(rs.getString("second_line_address"));
            line.add(rs.getString("city"));
            listOfResults.add(Tuple.tuple(id, line));
        }

        close();
        return listOfResults;
    }

    public void updateServiceProviderStatus(ServiceProviderStatus status, String serviceId) throws SQLException {
        init();

        stmt.executeUpdate("UPDATE service_provider SET approved_status = '"+status+"'" +
                " WHERE  s_id = '"+serviceId+"'");

        close();
    }

}
