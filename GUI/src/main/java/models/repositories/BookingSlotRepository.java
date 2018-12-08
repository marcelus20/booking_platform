package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Booking;
import models.tuples.entitiesRepresentation.BookingSlots;
import models.tuples.Tuple;
import models.utils.MyCustomDateAndTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingSlotRepository implements Repository<BookingSlots> {


    private Repository<Booking> bRep;

    public BookingSlotRepository(){
        bRep = new BookingRepository();
    }


    @Override
    public void addToDB(BookingSlots obj) throws SQLException {

    }

    @Override
    public BookingSlots selectObjById(Object id) throws SQLException {

        BookingSlots slot = new BookingSlots();
        Booking b = new Booking();



        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM booking_slots WHERE id = "+id+";");

        while (rs.next()){
            slot.withTimestamp(rs.getTimestamp("time_stamp"));
            slot.withServiceId(rs.getString("s_id"));
            slot.withAvailability(rs.getBoolean("availability"));
        }

        if(!slot.getAvailability()){
            b = (Booking) bRep.selectObjById(id);
        }

        slot.withBooking(b);


        return slot;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {
        return null;
    }

    @Override
    public List<BookingSlots> getList(AbstraticUser user) throws SQLException {
        return null;
    }


    public void updateBookingSlotAvailability(Tuple<String, String> id, Boolean status) throws SQLException {

        Integer i = 0;

        if(status){
            i = 1;
        }
        System.out.println(i);

        String query = "UPDATE booking_slots SET availability = "
                +i+" WHERE timestamp = '"+ id.get_1() + "' AND s_id = '"+id.get_2()+"' ;";
        System.out.println(query);

            Database.database().getStmt().executeUpdate(query);

    }

    public void addSlotsToDB(String id, List<MyCustomDateAndTime> selectedSlots) throws SQLException {


        selectedSlots.forEach(selectedSlot->{
            try {
                Database.database().getStmt().executeUpdate("INSERT INTO booking_slots values('"
                        +selectedSlot.getTimestamp()+"', "+ id +", '1') ;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public List<BookingSlots> getList(String id) throws SQLException {
        List<BookingSlots> slots = new ArrayList<>();

        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM booking_slots WHERE s_id = '"+id+"';");
        while (rs.next()){
            BookingSlots bookingSlots= new BookingSlots();
            bookingSlots.withServiceId(id);
            bookingSlots.withTimestamp(rs.getTimestamp("timestamp"));
            bookingSlots.withAvailability(rs.getBoolean("availability"));
            slots.add(bookingSlots);
        }

        return slots;
    }

    public void updateBookingSlotAvailability(Timestamp timestamp, String serviceId, boolean b) throws SQLException {

        Database.database().getStmt().executeUpdate("UPDATE booking_slots SET availability = "+b+" WHERE timestamp = " +
                "'"+timestamp+"' AND s_id = '"+serviceId+"' ;");

    }
}
