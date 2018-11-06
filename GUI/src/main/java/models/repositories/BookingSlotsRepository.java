package models.repositories;

import models.entities.BookingSlots;
import models.entities.ServiceProvider;
import models.users.AbstractUser;

import java.sql.SQLException;
import java.util.List;

public class BookingSlotsRepository extends Database implements Repository {

    public BookingSlotsRepository() throws SQLException {
        super();
    }

    @Override
    public void insertData(Object obj) {
        super.insertData(obj);
        BookingSlots b = (BookingSlots) obj;
        Integer availability;
        if(b.getAvailability()){
            availability = 1;
        }else{
            availability = 0;
        }
        try{
            String queryInsertionToServiceProviderTable = new StringBuilder()
                    .append("INSERT INTO booking_slots ")
                    .append("(timestamp, s_id, availability) ")
                    .append("VALUES( ")
                    .append("'").append(b.getTimestamp()).append("'")
                    .append(", ")
                    .append("'").append(b.getServiceId()).append("'")
                    .append(", ")
                    .append("'").append(availability).append("'")
                    .append(");")
                    .toString();

            System.out.println(queryInsertionToServiceProviderTable);


            myStmt.executeUpdate(queryInsertionToServiceProviderTable);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public List<List> selectAll() {
        return null;
    }

    @Override
    public List<List> executeSelectQuery(String query) {
        return null;
    }

    @Override
    public Long getId(AbstractUser user) {
        return null;
    }
}
