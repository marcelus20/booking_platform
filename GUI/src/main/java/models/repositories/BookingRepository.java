package models.repositories;

import models.Database;
import models.entitiesRepresentation.Booking;
import models.entitiesRepresentation.BookingSlots;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookingRepository extends Database implements Repository {

    public BookingRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }

    @Override
    public void addToDB(Object obj) throws SQLException {

    }

    @Override
    public Booking selectObjById(Object id) throws SQLException {

        Booking b = new Booking();

        init();

        ResultSet rs = stmt
                .executeQuery("SELECT * FROM booking_slots WHERE id = "
                        +((Tuple)id).get_2()+" AND time_stamp = "+((Tuple) id).get_1()+";");

        while (rs.next()){
            b.withBookingStatus(rs.getString("booking_status"));
            b.withComplaint(rs.getString("complaint"));
        }

        close();

        return b;
    }

    @Override
    public List<Booking> selectAll() {
        return null;
    }

    public void addBook(TupleOf3Elements<String, String, String> tuple, BookingSlots b) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        init();
        System.out.println(b);

        String query = new StringBuilder().append("INSERT INTO booking VALUES (")
                .append("'").append(tuple.get_1()).append("', ")
                .append("'").append(tuple.get_2()).append("', ")
                .append("'").append(tuple.get_3()).append("', ")
                .append("'").append(b.getBooking().getBookingStatus()).append("', ")
                .append("'").append(b.getBooking().getComplaint()).append("'); ").toString();

        System.out.println(query);

        stmt.executeUpdate(query);

        Repository<BookingSlotRepository> bRep = new BookingSlotRepository();

        ((BookingSlotRepository) bRep).updateBookingSlotAvailability(Tuple
                .tuple((String) tuple.get_1(), (String) tuple.get_3()), !b.getAvailability());

        close();
    }
}
