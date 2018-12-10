package models.utils;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import models.enums.ServiceProviderStatus;
import models.repositories.LogRepository;
import models.repositories.Repository;
import models.tuples.entitiesRepresentation.*;
import models.tuples.joinedEntities.ManageBookingView;
import models.tuples.joinedEntities.ServiceProviderTableView;
import org.apache.commons.codec.digest.DigestUtils;
import views.dashboard.Dashboard;
import javax.swing.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

/**
 * This class is a collection of useful methods that may be used throughout the whole project.
 * This do not represent a specific blueprint.
 */
public class Tools {

    /**
     * Pops up an error message dialog
     * @param frame
     * @param message
     * @param title
     */
    public static void alertError(JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Pops up a warning JOptionPane dialog with a message
     * @param frame
     * @param message
     * @param title
     */
    public static void alertWarning( JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Pops up  GUI plain message dialog
     * @param frame
     * @param message
     * @param title
     */
    public static void alertMsg(JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * This is the function to validate a form in general.
     * Because of the fact that each for is defferent from each other, and they follow different rules,
     * It takes a callable function as parameter and this function is to be implemented in the moment of usage
     * (lambda expression)
     * @param f
     * @throws Exception
     */
    public static void formValidator(Callable f) throws Exception {
        f.call();
    }

    /**
     * Checks if pass is equals to confirmPass.
     * @param pass
     * @param confirmPass
     * @return
     */
    public static Boolean validatePasswordsInput(String pass, String confirmPass){
        return pass.equals(confirmPass);
    }

    /**
     * Checks if password meet the criteria of Strange symbol, upper case and it is between 8 and 12 length
     * @param pass
     * @return
     */
    public static Boolean validatePasswordCriteria(String pass){
        return pass.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{8,12})");
    }


    /**
     * checks if email has an email pattern regex
     * @param email
     * @return
     */
    public static Boolean validateEmail(String email) {
        return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(email).find();
    }

    /**
     * checks if string is greater than 3
     * @param str
     * @return
     */
    public static Boolean validateStringsNonSmallerEqualsThan3(String str){
        return str.length() > 3;
    }

    /**
     * checks if str as numbers in it.
     * @param str
     * @return
     */
    public static Boolean validateNotNumericStrings(String str){
        return str.matches("[A-Za-z ]+") && validateStringsNonSmallerEqualsThan3(str);
    }

    /**
     * Hashing pass to MD5 apache.comonsDigestUtils library
     * @param pass to be turned into md5 hash
     * @return the hashed password
     */
    public static String hashingPassword(String pass){
        return DigestUtils.md5Hex(pass).toUpperCase();
    }

    /**
     * pops a message dialog with a combo box with options
     * @param d
     * @param options
     * @param msg
     * @param title
     * @return
     */
    public static String alertComboBox(Dashboard d, String[] options, String msg, String title){
        return (String) JOptionPane.showInputDialog(d, msg,
                title, JOptionPane.PLAIN_MESSAGE, null,  options, "Dublin");
    }


    /**
     * pops a message dialog with a Yes button to confirm operation
     * @param d
     * @param msg
     * @return
     */
    public static Integer alertConfirm(Dashboard d, String msg){
        return JOptionPane.showConfirmDialog(d, msg,
                "Booking", JOptionPane.YES_NO_OPTION);
    }


    /**
     * Filters the list of bookings slots to just slots available.
     * @param fullBookingSlots
     * @return
     */
    public static List<BookingSlots> filterBookingsToJustAvailables(List<BookingSlots> fullBookingSlots) {
    List<BookingSlots> filteredBookingSlots = new ArrayList<>();
    fullBookingSlots.forEach(slot->{ //for each slot, add just the ones that are available
        if(slot.getAvailability()){
            filteredBookingSlots.add(slot);
        }
    });
    //return list of slots
    return filteredBookingSlots;
    }


    /**
     * THis method returns the enum  correspondent to the String passed by parameter.
     * This is to be used when the column retrieved from DB is a string and needs to be
     * interpreted as an enum here.
     * @param newStatus
     * @return
     */
    public static BookingStatus mapBookingStatusStringToEnum(String newStatus){
        if(newStatus.equals(String.valueOf(BookingStatus.CONFIRMED))){
            return BookingStatus.CONFIRMED;
        }else if(newStatus.equals(String.valueOf(BookingStatus.PENDENT))){
            return BookingStatus.PENDENT;
        }else if (newStatus.equals(String.valueOf(BookingStatus.USER_DID_NOT_ARRIVE)) || newStatus.contains("arrive")){
            return BookingStatus.USER_DID_NOT_ARRIVE;
        }else if(newStatus.equals(String.valueOf(BookingStatus.COMPLETE))){
            return BookingStatus.COMPLETE;
        }else{
            return BookingStatus.PENDENT;
        }
    }

    /**
     * This method returns a list of the string version of the BookingStatusEnum
     * Using my class ArrayListBuilder
     * @return
     */
    public static List<String>getStringListOfAllBookingStatus(){
        return new ArrayListBuilder<String>()
                .add(String.valueOf(BookingStatus.PENDENT))
                .add(String.valueOf(BookingStatus.CONFIRMED))
                .add(String.valueOf(BookingStatus.COMPLETE))
                .add(String.valueOf(BookingStatus.USER_DID_NOT_ARRIVE)).build();
    }

    /**
     * This method returns the string version of the enum ServiceproviderStatus in an array.
     * @return
     */
    public static String[] getStringLIstOfAllServiceProviderStatus() {
        return new String[]{String.valueOf(ServiceProviderStatus.APPROVED)
                , String.valueOf(ServiceProviderStatus.REJECTED),
                String.valueOf(ServiceProviderStatus.PENDENT)};
    }

    /**
     * this method wraps a given string within html tags. That's for the JLabel classes
     * @param str
     * @return
     */
    public static String wrapStringWithHtml(String str){
        return "<html><body>"+str+"</body></html>";
    }

    /**
     * generates an array of review enum states.
     * @return
     */
    public static BookingReview[] generateArrayOfBookingReview() {

        return new ArrayListBuilder<BookingReview>().add(BookingReview.NO_REVIEW_ADDED,
                BookingReview.END_OF_THE_WORLD, BookingReview.TERRIBLE, BookingReview.BAD,
                BookingReview.MEH, BookingReview.OK, BookingReview.GOOD, BookingReview.VERY_GOOD,
                BookingReview.SUPERB, BookingReview.PERFECT).build().toArray(new BookingReview[]{});
    }


    /**
     * Creates a text of randoms chars with the length of 7 and returns this generated text.
     * The random chars go from upper/lower case and numbers!
     * @return
     */
    public static String createRandomText() {
        String randomText = "";
        //list of possible characters.
        List<Character> characters = Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u',
                'v','w','x','y','z','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F','G','H','I','J','K',
                'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');

        for(int i = 0; i < 7; i++){
            Collections.shuffle(characters); // shuffle the the list
            //concatenate with new random selected text
            randomText += characters.get(new Random().nextInt(characters.size()));
        }
        return randomText; //return the random string
    }

    /**
     * This method recods a log and records it to database using the logRepository db communicator.
     * @param log to be recorded in the log table.
     */
    public static void recordALogToDB(Log log){
        Repository<Log> logRepository = new LogRepository();
        try {
            logRepository.addToDB(log);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
