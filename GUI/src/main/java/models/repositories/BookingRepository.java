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


/**
 * This is an implementation of the Repository class. Aside from the override methods, it contains the  its own methods.
 */
public class BookingRepository implements Repository<Booking> {

    /**
     * empty constructor
     */
    public BookingRepository(){
    }


    /**
     * this override method turned out not to be used, As Booking is a more complex object, if has been used the AddBook() method instead
     * @param obj
     * @throws SQLException
     */
    @Override
    public void addToDB(Booking obj) throws SQLException {

    }

    /**
     * This mehtod will get the a tuple of 2 elements, with the timestamp and id of service provider that owns the booking.
     * with these id inserted in the query, it finds the object in the db, maps to Booking object and returns the object.
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Booking selectObjById(Object id) throws SQLException {
        Booking b = new Booking();
        //the id is timestamp and service provider ID
        ResultSet rs = Database.database().getStmt()
                .executeQuery("SELECT * FROM booking_slots WHERE id = "
                        +((Tuple)id).get_2()+" AND time_stamp = "+((Tuple) id).get_1()+";");

        while (rs.next()){
            b.withBookingStatus(Tools.mapBookingStatusStringToEnum(rs.getString("booking_status")));
            b.withReview(BookingReview.valueOf(rs.getString("complaint")));
        }
        return b;
    }

    /**
     * this method does not apply to Booking cause it is not a user
     * @param email
     * @return
     * @throws SQLException
     */
    @Override
    public String selectIdOfUser(String email) throws SQLException {
        return null;
    }


    /**
     * This function will add a boooking to booking table in the DB by receiving a slot
     * and the customer that booked that slot
     * @param slot
     * @param user
     */
    public void addBook(BookingSlots slot, Customer user){
        try {
            //prepating the query
            String query = new StringBuilder().append("INSERT INTO booking VALUES (")
                    .append("'").append(slot.getTimestamp()).append("', ")
                    .append("'").append(slot.getServiceId()).append("', ")
                    .append("'").append(user.getId()).append("', ")
                    .append("'").append(BookingStatus.PENDENT).append("', ")
                    .append("'").append(BookingReview.NO_REVIEW_ADDED).append("'); ").toString();

            //executing the query
            Database.database().getStmt().executeUpdate(query);
            //after booking has been inserted to db, next step is to set that slot availability to false.
            BookingSlotRepository bookingSlotsRepository = new BookingSlotRepository(); // creating the slot communicator
            //setting availability to false
            bookingSlotsRepository.updateBookingSlotAvailability(slot.getTimestamp(), slot.getServiceId(), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function returns the list of all Booking belonged to the customer passed as parameter
     * @param customer
     * @return
     * @throws SQLException
     */
    @Override
    public List<Booking> getList(AbstraticUser customer) throws SQLException {
        List<Booking> bookings = new ArrayList<>(); // initialising list

        //preparing query
        String query = "SELECT * FROM booking WHERE id = "+customer.getId()+";";

        //executing query
        ResultSet rs = Database.database().getStmt().executeQuery(query);

        //mapping result set with booking objects and adding the booking to the list
        while (rs.next()){
            Booking booking = new Booking();
            booking.withCustomerId(customer.getId());//populating customer ID
            booking.withBookingStatus(BookingStatus.valueOf(rs.getString("booking_status"))); // populating bookin status
            booking.withReview(BookingReview.valueOf(rs.getString("booking_review")));// populating review
            bookings.add(booking); // adding this booking to bookings list
        }
        //return bookings list
        return bookings;
    }


    /**
     * This method will update a review by getting information from manageBokingView class and the new review for the optate
     * The ManageBookingView has the necessary information for the composition of the booking key table such as timestamp,
     * customer id and service provider id.
     * @param manageBookingView
     * @param review
     * @throws SQLException
     */
    public void updateAReview(ManageBookingView manageBookingView, String review) throws SQLException {
        Database.database().getStmt().executeUpdate("UPDATE booking SET review = '"+review+"' WHERE time_stamp = '"
                + manageBookingView.getTimestamp() + "' AND c_id = '"
                + manageBookingView.getCustomerId() + "' AND s_id = '"
                +manageBookingView.getServiceId() + "' ;");
    }

    /**
     * This method returns the all bookings belonged to the service provider passed as parameter.
     * In this case, all bookingStatus is needed, so the bookingStatus is set to null to bring them all
     * @param serviceProvider
     * @return
     */
    public List<ManageBookingView> selectAllBookingsFromServiceProvider(ServiceProvider serviceProvider){
            return generateBookingView(serviceProvider, null);
    }


    /**
     * This function works similarly like the updateAReview, but this one will update the status of bookings
     * Barbers may use this. Again, all the columns for the compose key of table is includded in the ManageBookingView
     * object.
     * @param manageBookingView
     * @param bStatus
     */
    public void updateABookingStatus(ManageBookingView manageBookingView, String bStatus){
        try {
            //turning string into an enum
            BookingStatus status = Tools.mapBookingStatusStringToEnum(bStatus);
            System.out.println(status);
            //updating the booking record in the database
            Database.database().getStmt().executeUpdate("UPDATE booking SET booking_status = '"+status+"' WHERE time_stamp = '"
                    + manageBookingView.getTimestamp() + "' AND c_id = '" + manageBookingView.getCustomerId() + "' AND s_id = '" +manageBookingView.getServiceId()+ "' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This function returns the BookingStatus of the records in the database.
     * @return
     */
    public List<String> selectDistinctsBookingStatus() {
        try {
            List<String> result = new ArrayList<>(); //declaring list
            ResultSet rs; // executing query
            rs = Database.database().getStmt().executeQuery("SELECT DISTINCT booking_status FROM booking;");
            while(rs.next()){
                result.add(rs.getString("booking_status"));// lopping through the result set
            }
            return result; // returning result
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * This function is used to cancel a booking. The booking with all necessary information is passed by paramenter
     * @param manageBookingView the booking to be canceled
     * @throws SQLException
     */
    public void cancelBooking(ManageBookingView manageBookingView) throws SQLException{
        //executing query
        Database.database().getStmt().executeUpdate("DELETE FROM booking WHERE time_stamp ='"+ manageBookingView.getTimestamp()+
                "' AND s_id = "+manageBookingView.getServiceId()+ " AND c_id = "
                + manageBookingView.getCustomerId()+";");
        //loading the BookingSlots table communicator
        Repository<BookingSlots> bRep = new BookingSlotRepository();
        //updating slot of that booking back to available.
        ((BookingSlotRepository) bRep).updateBookingSlotAvailability(manageBookingView
                .getTimestamp(), manageBookingView.getServiceId(), true);
    }

    /**
     *This function will select all bookings belonged to that service provider acording with the status passed by parameter
     * @param user
     * @param bookingStatus
     * @return the view panel that will display all the bookings
     */
    public List<ManageBookingView> selectAllBookingsFromServiceProvider(ServiceProvider user, BookingStatus bookingStatus){
       return generateBookingView(user, bookingStatus);
    }

    /**
     * This function get the result of the joined tables query: booking joined with service provider joined with phone
     * and compose the ManageBookingView object.
     * @param user
     * @param bookingStatus
     * @return
     */
    public List<ManageBookingView> generateBookingView(AbstraticUser user, BookingStatus bookingStatus){
        try {
            List<ManageBookingView> manageBookingViews = new ArrayList<>();//declare list of ManageBookingView
            //calling service provider and customer database communicator/
            Repository<ServiceProvider> serviceProviderRepository = new ServiceProviderRepository();
            Repository<Customer> customerRepository = new CustomerRepository();
            String userString = "";
            if(user.getUserType().equals(UserType.CUSTOMER)){
                userString = " b.c_id ";
            }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
                userString = " b.s_id ";
            }
            /**
             * because of the fact that booking status can be null, if it is not null, the bookingSentence variable will
             * be concatenated at the end of the query, filtering the results to that booking status
             */
            String bookingStatusSentence = "";
            if(bookingStatus != null){// this sentence will be concatenated with at the end of the query
                bookingStatusSentence += " AND booking_status = '"+bookingStatus+"' ";
            }
            //preparing query
            String query = "select * from booking b join service_provider s on b.s_id = s.s_id" +
                    " join phone_list p on p.id = b.s_id where "+userString+"  = "+user.getId()+" " +
                    bookingStatusSentence+" ;";
            ResultSet rs;
            //executin query
            rs = Database.database().getStmt().executeQuery(query);
            while(rs.next()){//loping through each line of result set
                System.out.println(customerRepository.selectObjById(rs.getString("c_id")));
                ManageBookingView manageBookingView = new ManageBookingView();// initialising the view object
                /**
                 * The lines bellow is for mapping each rs.getString of columns of db to the ManageBookingView object attribute
                 */
                manageBookingView.setServiceProvider(
                        serviceProviderRepository.selectObjById(
                                rs.getString("s_id")));// populate the hole service provide object
                manageBookingView.setCustomer(
                        customerRepository.selectObjById(//populate the whole customer object
                                rs.getString("c_id")));//populate customer id
                manageBookingView.setTimestamp(rs.getTimestamp("time_stamp")); // populate timestamp
                manageBookingView.setServiceId(rs.getString("s_id"));//populate service provider id
                manageBookingView.setCustomerId(rs.getString("c_id")); // populate the customer id
                manageBookingView.setReview(BookingReview.valueOf(rs.getString("review")));//populate review
                manageBookingView.setCompanyName(rs.getString("company_full_name"));// populate comany name
                manageBookingView.setBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));// populate status
                manageBookingView.setPhone(rs.getString("phone")); // populate phone
                manageBookingViews.add(manageBookingView); // add this object to the list
            }
            return manageBookingViews; // return the list
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is to make sure that all bookings bookings that are older than now, to be set automatically to complete
     * The service will not have to worry about setting each booking to complete. If another status comes up such as
     * User did not arrive, for example, the service can manually change the status
     */
    public static void updateAllOldBookingsToComplete(){
        try {
            Database.database().getStmt().executeUpdate("UPDATE booking SET booking_status = 'COMPLETE'" +
                    "WHERE time_stamp < NOW();"); // set to complete bookings with old timestamps
            /**
             * after this, delete all available slots that have not been booked by anaybody.
             * This will keep the table slots with smaller in terms of useless data.
             */
            Database.database().getStmt().executeUpdate("DELETE FROM booking_slots WHERE availability = TRUE AND timestamp < NOW();");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
