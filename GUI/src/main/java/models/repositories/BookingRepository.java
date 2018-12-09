package models.repositories;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import models.Database;
import models.enums.UserType;
import models.tuples.entitiesRepresentation.*;
import models.tuples.Tuple;
import models.tuples.joinedEntities.ManageBookingView;
import models.utils.Tools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements Repository<Booking> {

    public BookingRepository(){
    }


    @Override
    public void addToDB(Booking obj) throws SQLException {

    }

    @Override
    public Booking selectObjById(Object id) throws SQLException {

        Booking b = new Booking();

        ResultSet rs = Database.database().getStmt()
                .executeQuery("SELECT * FROM booking_slots WHERE id = "
                        +((Tuple)id).get_2()+" AND time_stamp = "+((Tuple) id).get_1()+";");

        while (rs.next()){
            b.withBookingStatus(Tools.mapBookingStatusStringToEnum(rs.getString("booking_status")));
            b.withReview(BookingReview.valueOf(rs.getString("complaint")));
        }

        return b;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {
        return null;
    }


    public void addBook(BookingSlots slot, Customer user) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        String query = new StringBuilder().append("INSERT INTO booking VALUES (")
                .append("'").append(slot.getTimestamp()).append("', ")
                .append("'").append(slot.getServiceId()).append("', ")
                .append("'").append(user.getId()).append("', ")
                .append("'").append(BookingStatus.PENDENT).append("', ")
                .append("'").append(BookingReview.NO_REVIEW_ADDED).append("'); ").toString();

        Database.database().getStmt().executeUpdate(query);

        BookingSlotRepository bookingSlotsRepository = new BookingSlotRepository();

        bookingSlotsRepository.updateBookingSlotAvailability(slot.getTimestamp(), slot.getServiceId(), false);

    }

    @Override
    public List<Booking> getList(AbstraticUser customer) throws SQLException {
        List<Booking> bookings = new ArrayList<>();


        String query = "SELECT * FROM booking WHERE id = "+customer.getId()+";";

        ResultSet rs = Database.database().getStmt().executeQuery(query);

        while (rs.next()){
            Booking booking = new Booking();
            booking.withCustomerId(customer.getId());
            booking.withBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));
            booking.withReview(BookingReview.valueOf(rs.getString("booking_review")));
        }

        return bookings;
    }


    public void updateAReview(ManageBookingView manageBookingView, String review) throws SQLException {

        Database.database().getStmt().executeUpdate("UPDATE booking SET review = '"+review+"' WHERE time_stamp = '"
                + manageBookingView.getTimestamp() + "' AND c_id = '"
                + manageBookingView.getCustomerId() + "' AND s_id = '"
                +manageBookingView.getServiceId() + "' ;");

    }

    public List<ManageBookingView> selectAllBookingsFromServiceProvider(ServiceProvider serviceProvider){
            return generateBookingView(serviceProvider, null);
    }

    public void updateABookingStatus(ManageBookingView manageBookingView, String bStatus){
        try {
            BookingStatus status = Tools.mapBookingStatusStringToEnum(bStatus);
            System.out.println(status);
            Database.database().getStmt().executeUpdate("UPDATE booking SET booking_status = '"+status+"' WHERE time_stamp = '"
                    + manageBookingView.getTimestamp() + "' AND c_id = '" + manageBookingView.getCustomerId() + "' AND s_id = '" +manageBookingView.getServiceId()+ "' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> selectDistinctsBookingStatus() {
        try {
            List<String> result = new ArrayList<>();
            ResultSet rs = null;
            rs = Database.database().getStmt().executeQuery("SELECT DISTINCT booking_status FROM booking;");
            while(rs.next()){
                result.add(rs.getString("booking_status"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void cancelBooking(ManageBookingView manageBookingView) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {


        Database.database().getStmt().executeUpdate("DELETE FROM booking WHERE time_stamp ='"+ manageBookingView.getTimestamp()+
                "' AND s_id = "+manageBookingView.getServiceId()+ " AND c_id = "
                + manageBookingView.getCustomerId()+";");

        Repository<BookingSlots> bRep = new BookingSlotRepository();


        ((BookingSlotRepository) bRep).updateBookingSlotAvailability(manageBookingView
                .getTimestamp(), manageBookingView.getServiceId(), true);

    }

    public List<ManageBookingView> selectAllBookingsFromServiceProvider(ServiceProvider user, BookingStatus bookingStatus){
       return generateBookingView(user, bookingStatus);
    }

    public List<ManageBookingView> generateBookingView(AbstraticUser user, BookingStatus bookingStatus){
        try {
            List<ManageBookingView> manageBookingViews = new ArrayList<>();
            Repository<ServiceProvider> serviceProviderRepository = new ServiceProviderRepository();
            Repository<Customer> customerRepository = new CustomerRepository();
            String userString = "";
            if(user.getUserType().equals(UserType.CUSTOMER)){
                userString = " b.c_id ";
            }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
                userString = " b.s_id ";
            }
            String bookingStatusSentence = "";
            if(bookingStatus != null){
                bookingStatusSentence += " AND booking_status = '"+bookingStatus+"' ";
            }
            String query = "select * from booking b join service_provider s on b.s_id = s.s_id" +
                    " join phone_list p on p.id = b.s_id where "+userString+"  = "+user.getId()+" " +
                    bookingStatusSentence+" ;";
            ResultSet rs;
            rs = Database.database().getStmt().executeQuery(query);
            while(rs.next()){
                System.out.println(customerRepository.selectObjById(rs.getString("c_id")));
                ManageBookingView manageBookingView = new ManageBookingView();
                manageBookingView.setServiceProvider(
                        serviceProviderRepository.selectObjById(
                                rs.getString("s_id")));
                manageBookingView.setCustomer(
                        customerRepository.selectObjById(
                                rs.getString("c_id")));
                manageBookingView.setTimestamp(rs.getTimestamp("time_stamp"));
                manageBookingView.setServiceId(rs.getString("s_id"));
                manageBookingView.setCustomerId(rs.getString("c_id"));
                manageBookingView.setReview(BookingReview.valueOf(rs.getString("review")));
                manageBookingView.setCompanyName(rs.getString("company_full_name"));
                manageBookingView.setBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));
                manageBookingView.setPhone(rs.getString("phone"));
                manageBookingViews.add(manageBookingView);
            }
            return manageBookingViews;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void updateAllOldBookingsToComplete(){
        try {
            Database.database().getStmt().executeUpdate("UPDATE booking SET booking_status = 'COMPLETE'" +
                    "WHERE time_stamp < NOW();");
            Database.database().getStmt().executeUpdate("DELETE FROM booking_slots WHERE availability = TRUE AND timestamp < NOW();");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
