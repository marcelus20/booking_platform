package models.repositories;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import models.Database;
import models.tuples.entitiesRepresentation.*;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
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

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()){
            Booking booking = new Booking();
            booking.withCustomerId(customer.getId());
            booking.withBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));
            booking.withReview(BookingReview.valueOf(rs.getString("booking_review")));
        }

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

    public List<ManageBookingView> selectAllBookingsFromServiceProvider(ServiceProvider serviceProvider) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        return new Database().generateBookingView(serviceProvider, null);
    }

    public void updateABookingStatus(ManageBookingView manageBookingView, String bStatus) throws SQLException {
        init();
        BookingStatus status = Tools.mapBookingStatusStringToEnum(bStatus);
        System.out.println(status);
        stmt.executeUpdate("UPDATE booking SET booking_status = '"+status+"' WHERE time_stamp = '"
                + manageBookingView.getTimestamp() + "' AND c_id = '" + manageBookingView.getCustomerId() + "' AND s_id = '" +manageBookingView.getServiceId()+ "' ;");
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

    public List<ManageBookingView> selectAllBookingsFromServiceProvider(ServiceProvider user, BookingStatus bookingStatus) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        return new Database().generateBookingView(user, bookingStatus);
    }
}
