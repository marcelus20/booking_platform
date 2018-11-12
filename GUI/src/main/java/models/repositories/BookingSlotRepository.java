package models.repositories;

import models.Database;
import models.entitiesRepresentation.Booking;
import models.entitiesRepresentation.BookingSlots;
import models.tuples.Tuple;
import models.users.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class BookingSlotRepository extends Database implements Repository {


    private Repository<BookingRepository> bRep;

    public BookingSlotRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        bRep = new BookingRepository();
    }

    @Override
    public void addToDB(Object obj) throws SQLException {

    }

    @Override
    public BookingSlots selectObjById(Object id) throws SQLException {

        BookingSlots slot = new BookingSlots();
        Booking b = new Booking();

        init();

        ResultSet rs = stmt.executeQuery("SELECT * FROM booking_slots WHERE id = "+id+";");

        while (rs.next()){
            slot.withTimestamp(rs.getTimestamp("time_stamp"));
            slot.withServiceId(rs.getString("s_id"));
            slot.withAvailability(rs.getBoolean("availability"));
        }

        if(!slot.getAvailability()){
            b = (Booking) bRep.selectObjById(id);
        }

        slot.withBooking(b);

        close();

        return slot;
    }

    @Override
    public List<BookingSlots> selectAll() {
        return null;
    }

    public void updateBookingSlotAvailability(Tuple<String, String> id, Boolean status) throws SQLException {
        init();

        Integer i = 0;

        if(status.equals(true)){
            i = 1;
        }
        System.out.println(i);

        String query = "UPDATE booking_slots SET availability = "
                +i+" WHERE timestamp = '"+ id.get_1() + "' AND s_id = '"+id.get_2()+"' ;";
        System.out.println(query);

            stmt.executeUpdate(query);

        close();
    }
}
