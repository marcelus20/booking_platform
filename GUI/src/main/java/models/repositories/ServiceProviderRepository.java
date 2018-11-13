package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.BookingSlots;
import models.tuples.entitiesRepresentation.Location;
import models.tuples.entitiesRepresentation.ServiceProvider;
import models.utils.Tools;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderRepository extends Database implements Repository{


    public ServiceProviderRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

    }

    @Override
    public void addToDB(Object obj) throws SQLException {
        init();
        ServiceProvider s = (ServiceProvider)obj;
        Location l = s.getLocation();

        s.withPassword(Tools.hashingPassword(s.getPassword()));

        //FOR THE NEW service provider
        String query = new StringBuilder()
                .append("INSERT INTO service_provider (password, email, date_of_account_creation,")
                .append(" phone, company_full_name, approved_status) VALUES ( ")
                .append("'").append(s.getPassword()).append("', ")
                .append("'").append(s.getEmail()).append("', ")
                .append("'").append(s.getDateCreated()).append("', ")
                .append("'").append(s.getPhone()).append("', ")
                .append("'").append(s.getCompanyFullName()).append("', ")
                .append("'").append(s.getApprovedStatus()).append("') ;").toString();


        stmt.executeUpdate(query);

        String newId = "";
        ResultSet rs = stmt.executeQuery("SELECT s_id FROM service_provider WHERE email = '"+s.getEmail() + "'; ");
        while (rs.next()){
            newId = rs.getString("s_id");
        }

        //FOR THE LOCATION WISE
        query = new StringBuilder()
                .append("INSERT INTO location (s_id, first_line_address, eir_code,")
                .append(" city, second_line_address) VALUES ( ")
                .append("'").append(newId).append("', ")
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
            serviceProvider.withId((String)id); serviceProvider.withEmail(rs.getString("email"));
            serviceProvider.withDateCreated(Date.valueOf(rs.getString("date_of_account_creation")));
            serviceProvider.withPhone(rs.getString("phone"));
            serviceProvider.withCompanyFullName(rs.getString("company_full_name"));
            serviceProvider.withApprovedStatus(rs.getString("approved_status"));
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

}
