package models.repositories;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Booking;
import models.tuples.entitiesRepresentation.BookingSlots;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
import models.tuples.entitiesRepresentation.Customer;
import models.tuples.joinedEntities.ManageBookingView;
import models.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository extends Database implements Repository<Booking> {

    public BookingRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }


    @Override
    public void addToDB(Booking obj) throws SQLException {

    }

    @Override
    public Booking selectObjById(Object id) throws SQLException {

        Booking b = new Booking();

        init();

        ResultSet rs = stmt
                .executeQuery("SELECT * FROM booking_slots WHERE id = "
                        +((Tuple)id).get_2()+" AND time_stamp = "+((Tuple) id).get_1()+";");

        while (rs.next()){
            b.withBookingStatus(Tools.mapBookingStatusStringToEnum(rs.getString("booking_status")));
            b.withReview(BookingReview.valueOf(rs.getString("complaint")));
        }

        close();

        return b;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {
        return null;
    }


    public void addBook(BookingSlots slot, Customer user) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        init();


        String query = new StringBuilder().append("INSERT INTO booking VALUES (")
                .append("'").append(slot.getTimestamp()).append("', ")
                .append("'").append(slot.getServiceId()).append("', ")
                .append("'").append(user.getId()).append("', ")
                .append("'").append(BookingStatus.PENDENT).append("', ")
                .append("'").append(BookingReview.NO_REVIEW_ADDED).append("'); ").toString();

        stmt.executeUpdate(query);

        Repository<BookingSlots> bRep = new BookingSlotRepository();

        ((BookingSlotRepository) bRep).updateBookingSlotAvailability(slot.getTimestamp(), slot.getServiceId(), false);

        close();
    }

    @Override
    public List<Booking> getList(AbstraticUser customer) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        init();

        String query = "SELECT * FROM booking WHERE id = "+customer.getId()+";";

        ResultSet rs = stmt.executeQuery("query");

        while (rs.next()){
            Booking booking = new Booking();
            booking.withCustomerId(customer.getId());
            booking.withBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));
            booking.withReview(BookingReview.valueOf(rs.getString("booking_review")));
        }

//
//        ((Customer)customer)


//        String query = new StringBuilder().append("SELECT b.time_stamp, b.s_id, b.customer_id, s.company_full_name, ")
//                .append("l.first_line_address, l.city, b.booking_status FROM booking b ")
//                .append("JOIN service_provider s ON b.s_id = s.s_id JOIN location l ON ")
//                .append("l.s_id = s.s_id WHERE b.customer_id = '"+customerId+"';").toString();
//
//        ResultSet rs = stmt.executeQuery(query);
//        while (rs.next()){
//            List<String> line = new ArrayList<>();
//            TupleOf3Elements<String, String, String> t;
//            t = TupleOf3Elements.tupleOf3Elements(
//                    rs.getString("b.time_stamp"), rs.getString("b.s_id"), rs.getString("b.customer_id"));
//
//            line.add(rs.getString("time_stamp"));
//            line.add(rs.getString("company_full_name"));
//            line.add(rs.getString("first_line_address"));
//            line.add(rs.getString("city"));
//            line.add(rs.getString("booking_status"));
//
//            result.add(Tuple.tuple(t,line));
//        }
//
        close();
        return bookings;
    }


    public void updateAReview(ManageBookingView manageBookingView, String review) throws SQLException {

        init();

        stmt.executeUpdate("UPDATE booking SET review = '"+review+"' WHERE time_stamp = '"
                + manageBookingView.getTimestamp() + "' AND c_id = '"
                + manageBookingView.getCustomerId() + "' AND s_id = '"
                +manageBookingView.getServiceId() + "' ;");


        close();
    }

    public List<Tuple<TupleOf3Elements<String, String, String>, List<String>>>selectAllBookingsFromServiceProvider(String serviceId) throws SQLException {

        List<Tuple<TupleOf3Elements<String, String, String>, List<String>>>result= new ArrayList<>();
        init();



        String query = new StringBuilder().append("SELECT b.time_stamp, b.s_id, b.customer_id, c.first_name, ")
                .append("c.last_name, c.phone, b.booking_status FROM booking b ")
                .append("JOIN service_provider s ON b.s_id = s.s_id JOIN customers c ON ")
                .append("b.customer_id = c.customer_id WHERE b.s_id = '"+serviceId+"' ")
                .append(" AND booking_status = '"+BookingStatus.PENDENT+"' OR booking_status = '" +BookingStatus.CONFIRMED+
                        "' ORDER BY time_stamp;").toString();
        System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            mapResultSetToList(rs, result);
        }

        close();
        return result;
    }

    public List<Tuple<TupleOf3Elements<String, String, String>, List<String>>>selectAllBookingsFromServiceProvider(String serviceId, BookingStatus bookingStatus) throws SQLException {

        List<Tuple<TupleOf3Elements<String, String, String>, List<String>>>result= new ArrayList<>();
        init();

        String query = new StringBuilder().append("SELECT b.time_stamp, b.s_id, b.customer_id, c.first_name, ")
                .append("c.last_name, c.phone, b.booking_status FROM booking b ")
                .append("JOIN service_provider s ON b.s_id = s.s_id JOIN customers c ON ")
                .append("b.customer_id = c.customer_id WHERE b.s_id = '"+serviceId+"' ")
                .append(" AND booking_status = '"+bookingStatus+"' ORDER BY time_stamp;").toString();
        System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            mapResultSetToList(rs, result);
        }

        close();
        return result;
    }



    private void mapResultSetToList(ResultSet rs, List<Tuple<TupleOf3Elements<String, String, String>, List<String>>> result) throws SQLException {
        List<String> line = new ArrayList<>();
        TupleOf3Elements<String, String, String> t;
        t = TupleOf3Elements.tupleOf3Elements(
                rs.getString("b.time_stamp"), rs.getString("b.s_id"), rs.getString("b.customer_id"));
        line.add(rs.getString("time_stamp"));
        line.add(rs.getString("first_name"));
        line.add(rs.getString("last_name"));
        line.add(rs.getString("phone"));
        line.add(rs.getString("booking_status"));

        result.add(Tuple.tuple(t,line));
    }

    public void updateABookingStatus(TupleOf3Elements<String, String, String> id, String bStatus) throws SQLException {
        init();

        BookingStatus status = Tools.mapBookingStatusStringToEnum(bStatus);
        System.out.println(status);
        stmt.executeUpdate("UPDATE booking SET booking_status = '"+status+"' WHERE time_stamp = '"
                + id.get_1() + "' AND customer_id = '" + id.get_3() + "' AND s_id = '" +id.get_2() + "' ;");
        close();
    }

    public List<String> selectDistinctsBookingStatus() throws SQLException {
        List<String> result = new ArrayList<>();
        init();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT booking_status FROM booking;");
        while(rs.next()){
            result.add(rs.getString("booking_status"));
        }
        close();
        return result;
    }


    public void cancelBooking(ManageBookingView manageBookingView) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        init();

        stmt.executeUpdate("DELETE FROM booking WHERE time_stamp ='"+ manageBookingView.getTimestamp()+
                "' AND s_id = "+manageBookingView.getServiceId()+ " AND c_id = "
                + manageBookingView.getCustomerId()+";");

        Repository<BookingSlots> bRep = new BookingSlotRepository();


        ((BookingSlotRepository) bRep).updateBookingSlotAvailability(manageBookingView
                .getTimestamp(), manageBookingView.getServiceId(), true);

        close();
    }
}
