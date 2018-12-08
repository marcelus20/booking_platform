package models.utils;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import models.enums.ServiceProviderStatus;
import models.tuples.entitiesRepresentation.*;
import models.tuples.joinedEntities.ManageBookingView;
import models.tuples.joinedEntities.ServiceProviderTableView;
import org.apache.commons.codec.digest.DigestUtils;
import views.dashboard.Dashboard;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class Tools {

    public static void alertError(JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void alertWarning( JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void alertMsg(JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static void formValidator(Callable f) throws Exception {
        f.call();
    }

    public static Boolean validatePasswordsInput(String pass, String confirmPass){
        return pass.equals(confirmPass);
    }

    public static Boolean validatePasswordCriteria(String pass){
        return pass.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{8,12})");
    }


    public static Boolean validateEmail(String email) {
        return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(email).find();
    }

    public static Boolean validateStringsNonSmallerEqualsThan3(String str){
        return str.length() > 3;
    }

    public static Boolean validateNotNumericStrings(String str){

        return str.matches("[A-Za-z ]+") && validateStringsNonSmallerEqualsThan3(str);
    }

    //Hashing password to MD5 using the DigestUtils from apache.commons
    public static String hashingPassword(String pass){
        return DigestUtils.md5Hex(pass).toUpperCase();
    }

    public static String alertComboBox(Dashboard d, String[] options, String msg, String title){
        return (String) JOptionPane.showInputDialog(d, msg,
                title, JOptionPane.PLAIN_MESSAGE, null,  options, "Dublin");
    }

    public static Integer alertConfirm(Dashboard d, String msg){
        return JOptionPane.showConfirmDialog(d, msg,
                "Booking", JOptionPane.YES_NO_OPTION);
    }

    public static String[][] convertListOfBookingSlotsToArray(List<BookingSlots> slots){
        String[][] table = new String[slots.size()][3];
        for(int i = 0; i < slots.size(); i++){
            for(int j = 0; j < 2; j++){
                table[i][0]  = String.valueOf(slots.get(i).getTimestamp());
                table[i][1] = slots.get(i).getServiceId();
                table[i][2] = String.valueOf(slots.get(i).getAvailability());
            }
        }
        return table;
    }

    public static List<BookingSlots> filterBookingsToJustAvailables(List<BookingSlots> fullBookingSlots) {
    List<BookingSlots> filteredBookingSlots = new ArrayList<>();
    fullBookingSlots.forEach(slot->{
        if(slot.getAvailability()){
            filteredBookingSlots.add(slot);
        }
    });

    return filteredBookingSlots;
    }


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

    public static List<String>getStringListOfAllBookingStatus(){
        return new ArrayListBuilder<String>()
                .add(String.valueOf(BookingStatus.PENDENT))
                .add(String.valueOf(BookingStatus.CONFIRMED))
                .add(String.valueOf(BookingStatus.COMPLETE))
                .add(String.valueOf(BookingStatus.USER_DID_NOT_ARRIVE)).build();
    }

    public static String[] getStringLIstOfAllServiceProviderStatus() {

        return new String[]{String.valueOf(ServiceProviderStatus.APPROVED)
                , String.valueOf(ServiceProviderStatus.REJECTED),
                String.valueOf(ServiceProviderStatus.PENDENT)};
    }

    public static String wrapStringWithHtml(String str){


        return "<html><body>"+str+"</body></html>";
    }

    public static String[][] convert2DlistTo2DArray(List<ServiceProviderTableView> results) {
        String[][] table;
        try{
            table = new String[results.size()][results.get(0).getClass().getDeclaredFields().length];
        }catch (Exception e){
            return new String[0][0];
        }

        for(int i = 0; i < results.size(); i++){
            table[i][0] = results.get(i).getServiceId();
            table[i][1] = results.get(i).getServiceEmail();
            table[i][2] = results.get(i).getServiceName();
            table[i][3] = results.get(i).getAddress();
            table[i][4] = results.get(i).getCity();
            table[i][5] = results.get(i).getPhone();
        }
        return table;
    }

    public static String[][] mapManageBookingCustomerViewsListToArray(List<ManageBookingView> manageBookingViewList) {
        String[][] table;
        try{
            table = new String[manageBookingViewList.size()][manageBookingViewList.get(0).getClass().getDeclaredFields().length];
        }catch (Exception e){
            return new String[0][0];
        }

        for(int i = 0; i < manageBookingViewList.size(); i++){
            table[i][0] = String.valueOf(manageBookingViewList.get(i).getTimestamp());
            table[i][1] = manageBookingViewList.get(i).getCompanyName();
            table[i][2] = String.valueOf(manageBookingViewList.get(i).getBookingStatus());
            table[i][3] = manageBookingViewList.get(i).getPhone();
            table[i][4] = String.valueOf(manageBookingViewList.get(i).getReview());
        }

        return table;
    }

    public static String[][] mapManageBookingViewsListToArrayShortened(List<ManageBookingView> shortenedBookingViewList) {
        String[][] table;
        try{
            table = new String[shortenedBookingViewList.size()][3];
        }catch (Exception e){
            return new String[0][0];
        }

        for(int i = 0; i < shortenedBookingViewList.size(); i++){
            table[i][0] = String.valueOf(shortenedBookingViewList.get(i).getTimestamp());
            table[i][1] = shortenedBookingViewList.get(i).getCompanyName();
            table[i][2] = String.valueOf(shortenedBookingViewList.get(i).getReview());
        }
        return table;
    }

    public static BookingReview[] generateArrayOfBookingReview() {

        return new ArrayListBuilder<BookingReview>().add(BookingReview.NO_REVIEW_ADDED,
                BookingReview.END_OF_THE_WORLD, BookingReview.TERRIBLE, BookingReview.BAD,
                BookingReview.MEH, BookingReview.OK, BookingReview.GOOD, BookingReview.VERY_GOOD,
                BookingReview.SUPERB, BookingReview.PERFECT).build().toArray(new BookingReview[]{});
    }

    public static String[][] mapManageBookingServiceViewsListToArray(List<ManageBookingView> manageBookingViewList) {
        String[][] table;
        try{
            table = new String[manageBookingViewList.size()][manageBookingViewList.get(0).getClass().getDeclaredFields().length];
        }catch (Exception e){
            return new String[0][0];
        }

        for(int i = 0; i < manageBookingViewList.size(); i++){
            table[i][0] = String.valueOf(manageBookingViewList.get(i).getTimestamp());
            table[i][1] = manageBookingViewList.get(i).getCustomer().getFirstName();
            table[i][2] = manageBookingViewList.get(i).getCustomer().getLastName();
            table[i][3] = String.valueOf(manageBookingViewList.get(i).getBookingStatus());
            table[i][4] = manageBookingViewList.get(i).getCustomer().getPhone().getPhone();
            table[i][5] = String.valueOf(manageBookingViewList.get(i).getReview());
        }

        return table;
    }

    public static String[][] mapListOfProvidersToArray(List<ServiceProvider> providers) {

        String[][] table;
        try{
            table = new String[providers.size()][9];
        }catch (Exception e){
            return new String[0][0];
        }
//        "email", "company", "Date subscribed", "Status", "phone", "add line 1", "add line 2", "city", "eir code"
        for(int i = 0; i < providers.size(); i++){
            table[i][0] = String.valueOf(providers.get(i).getEmail());
            table[i][1] = providers.get(i).getCompanyFullName();
            table[i][2] = String.valueOf(providers.get(i).getDateCreated());
            table[i][3] = String.valueOf(providers.get(i).getStatus());
            table[i][4] = providers.get(i).getPhone().getPhone();
            table[i][5] = String.valueOf(providers.get(i).getLocation().getFirstLineAddress());
            table[i][6] = providers.get(i).getLocation().getSecondLineAddress();
            table[i][7] = providers.get(i).getLocation().getCity();
            table[i][8] = providers.get(i).getLocation().getEirCode();
        }

        return table;
    }

    public static String createRandomText() {
        String randomText = "";
        List<Character> characters = Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u',
                'v','w','x','y','z','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F','G','H','I','J','K',
                'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');

        for(int i = 0; i < 7; i++){
            Collections.shuffle(characters);
            randomText += characters.get(new Random().nextInt(characters.size()));
        }
        return randomText;
    }
}
