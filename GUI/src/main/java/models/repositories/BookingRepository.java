package models.repositories;

import models.Database;
import models.entitiesRepresentation.Booking;
import models.entitiesRepresentation.BookingSlots;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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



    public void addBook(TupleOf3Elements<String, String, String> tuple, BookingSlots b) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        init();
        System.out.println(b);

        String query = new StringBuilder().append("INSERT INTO booking VALUES (")
                .append("'").append(tuple.get_1()).append("', ")
                .append("'").append(tuple.get_2()).append("', ")
                .append("'").append(tuple.get_3()).append("', ")
                .append("'").append(b.getBooking().getBookingStatus()).append("', ")
                .append("'").append(b.getBooking().getComplaint()).append("'); ").toString();

        stmt.executeUpdate(query);

        Repository<BookingSlotRepository> bRep = new BookingSlotRepository();

        ((BookingSlotRepository) bRep).updateBookingSlotAvailability(Tuple
                .tuple((String) tuple.get_1(), (String) tuple.get_3()), !b.getAvailability());

        close();
    }

    public List<Tuple<TupleOf3Elements<String, String, String>, List<String>>>selectAllBookings(String customerId) throws SQLException {

        List<Tuple<TupleOf3Elements<String, String, String>, List<String>>>result= new ArrayList<>();
        init();

        String query = new StringBuilder().append("SELECT b.time_stamp, b.s_id, b.customer_id, s.company_full_name, ")
                .append("l.first_line_address, l.city, b.booking_status FROM booking b ")
                .append("JOIN service_provider s ON b.s_id = s.s_id JOIN location l ON ")
                .append("l.s_id = s.s_id WHERE b.customer_id = '"+customerId+"';").toString();

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            List<String> line = new ArrayList<>();
            TupleOf3Elements<String, String, String> t;
            t = TupleOf3Elements.tupleOf3Elements(
                    rs.getString("b.time_stamp"), rs.getString("b.s_id"), rs.getString("b.customer_id"));

            line.add(rs.getString("time_stamp"));
            line.add(rs.getString("company_full_name"));
            line.add(rs.getString("first_line_address"));
            line.add(rs.getString("city"));
            line.add(rs.getString("booking_status"));

            result.add(Tuple.tuple(t,line));
        }

        close();
        return result;
    }

    public void cancelBooking(TupleOf3Elements<String, String, String> id) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        init();

        stmt.executeUpdate("DELETE FROM booking WHERE time_stamp ='"+ id.get_1()+"' AND "+id.get_3()+ " AND "+ id.get_2()+";");

        Repository<BookingSlotRepository> bRep = new BookingSlotRepository();

        System.out.println(id);

        ((BookingSlotRepository) bRep).updateBookingSlotAvailability(Tuple
                .tuple((String) id.get_1(), (String) id.get_2()), true);

        close();
    }

    public void updateAComplaint(TupleOf3Elements<String, String, String> id, String complaint) throws SQLException {

        init();

        stmt.executeUpdate("UPDATE booking SET complaint = '"+complaint+"' WHERE time_stamp = '"
                + id.get_1() + "' AND customer_id = '" + id.get_2() + "' AND s_id = '" +id.get_3() + "' ;");


        close();
    }
}
