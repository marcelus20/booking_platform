/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */

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

/**
 * This class will interact with the booking_slots table in the database schema.
 * This implements the interface Repository of bookingSlots (Repository<BookingSlots>)#
 *
 * This class may also interact with the booking table, so the Repository<Booking> object will
 * also be used as an attribute of this class, the bRep object:
 */
public class BookingSlotRepository implements Repository<BookingSlots> {

    //declaring the bookingRepository object
    private Repository<Booking> bRep;

    /**
     * Constructor
     */
    public BookingSlotRepository(){
        // initialising bookingRepository
        bRep = new BookingRepository();
    }


    /**
     * Adds a bookingSlots to DB, may be implemented later
     * @param obj
     * @throws SQLException
     */
    @Override
    public void addToDB(BookingSlots obj) throws SQLException {

    }

    /**
     * This function selects will download the a bookingSlot from DB by finding it by the id passed.
     * The id is the id of service provider, not considering the timestamp of the slot.
     * @param id
     * @return
     * @throws SQLException
     */
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

    /**
     * This function will update a slot by a given tuple of ids (timestamp, service id) with the status of available
     * or not available (true or false)
     * @param id
     * @param status
     * @throws SQLException
     */
    public void updateBookingSlotAvailability(Tuple<String, String> id, Boolean status) throws SQLException {
        Integer i = 0;
        if(status){
            i = 1;
        }
        System.out.println(i); // preparing query
        String query = "UPDATE booking_slots SET availability = "
                +i+" WHERE timestamp = '"+ id.get_1() + "' AND s_id = '"+id.get_2()+"' ;";
        System.out.println(query);
            Database.database().getStmt().executeUpdate(query);
    }

    /**
     * This function adds a slot to the database taking into account the id of of the service provider
     * and the list of slots the service has made available in the GUI.
     * @param id
     * @param selectedSlots
     * @throws SQLException
     */
    public void addSlotsToDB(String id, List<MyCustomDateAndTime> selectedSlots) throws SQLException {
        selectedSlots.forEach(selectedSlot->{// for each slots in list, insert them into the database
            try {//execute query
                Database.database().getStmt().executeUpdate("INSERT INTO booking_slots values('"
                        +selectedSlot.getTimestamp()+"', "+ id +", '1') ;"); // 1 stands for true.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * this method gets the list of the Booking slots from Db
     */
    public List<BookingSlots> getList(String id) throws SQLException {
        List<BookingSlots> slots = new ArrayList<>();
        //executing the query to retrivve the booking slots with a given id
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM booking_slots WHERE s_id = '"+id+"';");
        while (rs.next()){
            BookingSlots bookingSlots= new BookingSlots();
            bookingSlots.withServiceId(id);
            bookingSlots.withTimestamp(rs.getTimestamp("timestamp"));
            bookingSlots.withAvailability(rs.getBoolean("availability"));
            slots.add(bookingSlots);
        }
        //return the list of slots
        return slots;
    }

    /**
     * This method will update a slot availability to available or not available, getting the timestamp and service ID.
     * of that booking
     * @param timestamp
     * @param serviceId
     * @param b
     * @throws SQLException
     */
    public void updateBookingSlotAvailability(Timestamp timestamp, String serviceId, boolean b) throws SQLException {

        Database.database().getStmt().executeUpdate("UPDATE booking_slots SET availability = "+b+" WHERE timestamp = " +
                "'"+timestamp+"' AND s_id = '"+serviceId+"' ;");

    }
}
