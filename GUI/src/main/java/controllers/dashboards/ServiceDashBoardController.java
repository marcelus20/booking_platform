package controllers.dashboards;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.CalendarSelectionListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import controllers.Application;
import controllers.Control;
import models.enums.BookingStatus;
import models.enums.ServiceProviderStatus;
import models.tuples.entitiesRepresentation.Log;
import models.tuples.entitiesRepresentation.ServiceProvider;
import models.repositories.BookingRepository;
import models.repositories.BookingSlotRepository;
import models.tuples.joinedEntities.ManageBookingView;
import models.utils.MyCustomDateAndTime;
import models.utils.Tools;
import views.customComponents.MyCustomFont;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;
import views.dashboard.serviceProvider.ServiceDashboard;
import views.dashboard.serviceProvider.SetBookingStatusView;
import views.dashboard.serviceProvider.SlotManagementPanel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class will load when a service provider type logs in the system.
 * As other dashboard controllers, this implements the interface Control
 * for the assignment of listeners to the buttons and inputs.
 * Services providers will be able to see upcomming bookings, cancel a booking, add
 * availables slots and so on.
 */
public class ServiceDashBoardController implements Control {


    /**
     * attributes
     */
    private final ServiceDashboard dashboard; //the dashboard view
    private final Application app;// the application controller instance
    private final ServiceProvider user; //
    private final BookingRepository bRep; // the booking agent to talk to database
    private final BookingSlotRepository bsRep; // the booking slots instance to talk to database
    private final List<MyCustomDateAndTime> selectedSlots; // the list of selected slots
    private String selectedItem;// item status that is selected when changing booking status


    /**
     * constructor, gets app as parameter to control the app class from here.
     * @param app
     */
    public ServiceDashBoardController(Application app){
        dashboard = new ServiceDashboard();//initialising dashboard view
        this.app = app;//populating the app instance
        user = (ServiceProvider) app.getUser();//casting Abstract user to service provider
        blockAllButtonsIfNotApproved();//making sure user can not browse if admin has not aprpoved
        showMessageOnHomePage(); // display messages according to user approval status
        setSideBarDashboardLayout();// setting grid Layout to dashboard side bar
        //mounting dashboard sidebar buttons by adding all of them to sidebar.
        dashboard.getButtonsPanel().forEach(b->dashboard.getSideBar().add(b.getButton()));
        greetUser();//displaying Hello message to user
        addButtonsAFunction();//adding all buttons a function (Override from control interface)
        addInputsAListener();//same for inputs
        bRep = new BookingRepository();//initialising instances that will talk to database
        bsRep = new BookingSlotRepository();
        selectedSlots = new ArrayList<>();
    }


    /**
     * If user object has an approval status of pendent or rejected, this function will set
     * All dashboards button not to enable, making sure service provider cannot browse through application
     */
    private void blockAllButtonsIfNotApproved() {
        /**
         * for each button in the array getButtonsPanels(), set to false if not approved.
         */
        dashboard.getButtonsPanel().forEach(b->{
            if(!(user.getStatus() == ServiceProviderStatus.APPROVED)){
                b.getButton().setEnabled(false);
            }
        });

    }

    /**
     * displays message according to the user approval status.
     * User can either be approved, rejected or pendent.
     */

    private void showMessageOnHomePage() {
        String msg = "";
        if(user.getStatus() == ServiceProviderStatus.APPROVED){// if approved
            msg = "Congratulations, your subscription has been approved<br>" +
                    "use the side bar buttons to browse through the system!";
        }else if(user.getStatus() == ServiceProviderStatus.REJECTED){//if rejected
            msg = "Unfortunately you have been rejected by one of the<br> administrators<br>" +
                    "Your datails seemed to be not genuine and discrepant";
        }else{//if pendent
            msg = "It worked! What do I do now? Soon, one of our administrators<br> will give you some feedback" +
                    "about your subscription status.<br> As this is a verification step, all options of the system will be " +
                    "blocked until status is changed!";
        }
        MyCustomJPanel panel = new MyCustomJPanel("Message", 700,700);
        panel.getContent().add(new MyCustomJLabel(msg, 20).getLabel());
        dashboard.getOutput().add(panel);// adding message to view
    }

    /**
     * sets the layout of the sidebar dashboard to grid layout
     */
    private void setSideBarDashboardLayout(){
        dashboard.getSideBar().setLayout(new GridLayout(20,1));
    }

    /**
     * Displays a Hello message along with the name of the user
     */
    private void greetUser(){
        dashboard.getSideBar().setTitle("Hello, "+user.getCompanyFullName());
    }

    /**
     * implementation of the Control interface methods. Assigns buttons in the dashboard side bar.
     * Services providers can go to upcomming bookings page, slots management or set a booking to a status.
     */
    @Override
    public void addButtonsAFunction() {
        dashboard.getButtonsPanel().forEach(b->{
            b.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getActionCommand().contains("pcoming")) {//go to upcomming bookings page
                        goToUpComingBookings();
                    }else if (e.getActionCommand().contains("vailable")){// go to slots management page
                        goToSlotsManagement();
                    }else if (e.getActionCommand().contains("complete")){// go to setting booking status page
                        goToSetBookingStatus();
                    }
                }
            });
        });
    }

    /**
     * Prepares view to receive booking status data from db and update view
     */
    private void goToSetBookingStatus() {
        switchDashboardPanelSetBookings();
    }

    /**
     * clear all dashboard output panel content and includes the booking status page into it
     */
    private void switchDashboardPanelSetBookings(){
        dashboard.getOutput().removeAll();
        dashboard.withSetBookingStatusView(new SetBookingStatusView());
        dashboard.getOutput().add(dashboard.getSetBookingStatusView());
        dashboard.validadeAndRepaint();
        mountSetBookingStatusView();
    }

    /**
     * Downloads status list from DB and populates the JCombo box with the status type.
     */

    private void mountSetBookingStatusView() {
        List<String> bookingStatusList = bRep.selectDistinctsBookingStatus();//getting all status type
        dashboard.getSetBookingStatusView().withBookingStatesFilter(new JComboBox(bookingStatusList.toArray()));//creating JCombo Box
        selectedItem = String.valueOf(dashboard.getSetBookingStatusView().getBookingStatesFilter().getSelectedItem());//populating selected Item attribute
        retrieveBookingsFromDB(selectedItem);//downloading bookings with the selected JCombo item
    }

    /**
     * gets all Bookings belonged to the logged service provider with the status passed as parameter.
     * Status is a string representation of the ENUM BookingStatus that holds states between Approved, Complete,
     * Pendent and userDidNotArrive
     * @param status
     */
    private void retrieveBookingsFromDB(String status) {
        /**
         * turning the plain string result come from db to the correspondent ENUM
         * eg: if string come from db = 'PENDENT' it will be converted to BookingStatus.PENDENT.
         */
        BookingStatus bookingStatus = Tools.mapBookingStatusStringToEnum(status);
        //retreiving bookings from db
        List<ManageBookingView> bookings = bRep.selectAllBookingsFromServiceProvider(user, bookingStatus);
        //creating JTable with the results obtained.
        createJTable(bookings);
    }

    /**
     * gets the list of ManageBookingView populated from the Database and converts into a
     * 2D array for the creation of the JTable before rendering to the view dashboard.
     * @param bookings
     */
    private void createJTable(List<ManageBookingView> bookings) {
        /**
         * turning the list of bookings into a 2D array by doing stream.map() lambda expression.
         * each attribute of a booking contained in bookings is being gatherd in an array
         * and all booking objetcts are also being gathered in a array, forming the 2 Darray.
         */
        String[][] table = bookings.stream().map(booking->
                Arrays.asList(booking.getTimestamp()+"",booking.getCustomer().getFirstName(),
                        booking.getCustomer().getLastName(), booking.getBookingStatus()+"",
                        booking.getCustomer().getPhone().getPhone(), booking.getReview()+"")
        .toArray(new String[6])).collect(Collectors.toList()).toArray(new String[bookings.size()][]);

        //creating the Jtable after the 2D array has been generated
        JTable jTable = new JTable(table, new String[]{"Date and Time", "Customer Name", "Customer surname", "Customer phone", "Status"});
        //creating the dictionary to map each row index to a correspondent object from the booking list
        Map<Integer, ManageBookingView> dictionary = mapTableWithManageBookingView(table, bookings);
        //finally fetching results to view dashboard
        showTableToView(jTable, dictionary);
    }

    /**
     * Fetchs the results retreived from DB passed by parameter.
     * @param jTable
     * @param dictionary
     */
    private void showTableToView(JTable jTable, Map<Integer, ManageBookingView> dictionary) {
        //populates dashboard with the table of data
        dashboard.getSetBookingStatusView().withBookingsTable(jTable);
        dashboard.validadeAndRepaint();
        addComboBoxAListener();//adds on item change event to combo box
        addTableAListener(jTable, dictionary);//add listener to table rows
    }

    /**
     * assigns a listener to the table of bookings and maps each row to the objects contained in
     * dictionary value.
     * @param jTable
     * @param dictionary
     */
    private void addTableAListener(JTable jTable, Map<Integer, ManageBookingView> dictionary) {
        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){//after value has adjusted, this prevents from triggering twice
                    //getting the string version of all Booking Status enum states
                    String[] statusList = Tools.getStringListOfAllBookingStatus().toArray(new String[]{});
                    //JCombo box to with the options of the status
                    String chosenOption = Tools.alertComboBox(dashboard, statusList, "Set status to..." , "Booking Status Setting");
                    //after changed the Jcombobox item, it will map to the booking selected in line and update information
                    //in database y using the bRep method updateABookingStatus with the chosen iption
                    bRep.updateABookingStatus(dictionary.get(jTable.getSelectedRow()), chosenOption);
                    // recording log to DB
                    Log log = new Log(user.getId(), user.getCompanyFullName()+" set a booking status to "+chosenOption);
                    Tools.recordALogToDB(log);
                    goToSetBookingStatus();// reload the booking status page, new value should be displayed.
                }
            }
        });
    }

    /**
     * Adding the JCombox with the booking status options a listener to the changing item
     */
    private void addComboBoxAListener() {
        dashboard.getSetBookingStatusView().getBookingStatesFilter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sets the field selectedItem with the JCombobox (bookingStatesFilter object)
                selectedItem = String.valueOf(dashboard.getSetBookingStatusView().getBookingStatesFilter().getSelectedItem());
                //loads again the download of bookings with the state changed in the JComboBox
                retrieveBookingsFromDB(selectedItem);
            }
        });
    }


    /**
     * Initialises the process of getting info from DB, mounting and fetching views result
     */
    private void goToSlotsManagement() {
        switchDashboardPanelToSlotsManagement();
    }


    /**
     * clears dashboard initialise the slot management panel and adds it to the output dashboard panel
     */
    private void switchDashboardPanelToSlotsManagement() {
        dashboard.getOutput().removeAll();//clearing output view panel
        dashboard.withSlotsManagementPanel(new SlotManagementPanel());// initialising slotManagementPanel attribute
        dashboard.getOutput().add(dashboard.getSlotManagementPanel());//adding the panel to the dashboard output
        dashboard.validadeAndRepaint();
        //The slot management panel has an attribute called CalendarPanel, so the line bellow
        //gives an listener to the changing state of the calendar
        addListenerToCalendarPanel(dashboard.getSlotManagementPanel().getCalendarPanel());
    }


    /**
     * Gives a listener to the calendar panel everytime a new Date is chosen and selected.
     * When selected, a new Panel should toggle in the same page, the slots table for the service provider
     * click and chose the slots.
     * @param calendarPanel
     */
    private void addListenerToCalendarPanel(CalendarPanel calendarPanel) {
        calendarPanel.addCalendarSelectionListener(new CalendarSelectionListener() {
            @Override
            public void selectionChanged(CalendarSelectionEvent calendarSelectionEvent) {
                setOutputSlotsTitle(calendarPanel.getSelectedDate());//setting the slotManagementPanel attribute title based on the selecte date
                toggleSlotsPanel(calendarSelectionEvent.getNewDate());//poping up panel with the slots to be chosen
            }
        });
    }


    /**
     *Sets the title of the Slots Management Panel based on the selected Date
     * @param selectedDate
     */
    private void setOutputSlotsTitle(LocalDate selectedDate) {
        dashboard.getSlotManagementPanel().getSlotOutputPanel().setTitle("Slots for the Date of "+ selectedDate);
    }

    /**
     * Toggles the slots panel after the date has been chosen.
     * @param newDate
     */
    private void toggleSlotsPanel(LocalDate newDate) {
        dashboard.getSlotManagementPanel().getContent()
                .add(dashboard.getSlotManagementPanel().getSlotOutputPanel());// adding the new panel to the output
        dashboard.validadeAndRepaint();//validate and repainting for the view to see changes
        clearSlotsPanels();//removes all from the panels contents
        clearSelectedSlotsField();//clears the list of slots that had been chosen before
        createSlotsDateAndTime(dashboard.getSlotManagementPanel().getCalendarPanel().getSelectedDate());
    }

    /**
     * clears the field selectedSlots in order to be used again to retrieve data from db
     */
    private void clearSelectedSlotsField() {
        selectedSlots.clear();
    }

    /**
     * Clears the 2 fields belonged to SlotManagementPanel:
     * The NotAvailableSlots view Panel and the Available Slotr view panel
     */
    private void clearSlotsPanels() {
        dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().removeAll();
        dashboard.getSlotManagementPanel().getAvailableSlots().getContent().removeAll();
    }


    /**
     * In order to create the booking slots, the text in the buttons should be generatared with a time
     * with the difference of 30 minutes, this means that the first button will be 8:00, the following will
     * be 8:30, than 9 and so on.
     * For this, this function loops through 24 steps and usgin the add30MinutesToTime function of the
     * MyCustomDateAndTime object, the times added 30 minutes are added generating a range of 24 buttons, from 8am to
     * 20 pm slots to be selected by service provider
     * @param selectedDate
     */
    private void createSlotsDateAndTime(LocalDate selectedDate) {
        MyCustomDateAndTime d = new MyCustomDateAndTime(Date.valueOf(selectedDate));
        List<MyCustomDateAndTime> dateAndTimes = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            dateAndTimes.add(d);
            d = d.add30MinutesToTime();//add 30 minutes every step
        }
        createSlotsButtons(dateAndTimes);// create buttons with the list of MyCustomDateAndTime objects
    }

    /**
     * Creates slots buttons with the timestamps passed as parameters
     * @param dateAndTimes
     */
    private void createSlotsButtons(List<MyCustomDateAndTime> dateAndTimes) {
        /**
         * loops through the list to create a button for each dateAndTimes objects
         */
        List<JButton> buttonList = dateAndTimes.stream()
                .map(dateAndTime -> new MyCustomJButton(String.valueOf(dateAndTime.getTime()))
                        .getButton()).collect(Collectors.toList());
        List<Time> times = createListOfTimes(dateAndTimes);// creates list of times
        generateDictionary(times, buttonList);// maps all times to the correspondent butonList
    }


    /**
     * Maps all times contained in the list of Times to the list of button list
     * @param times
     * @param buttonList
     */
    private void generateDictionary(List<Time> times, List<JButton> buttonList) {
        Map<Time,JButton> maps = new HashMap<>();// creating dictionary
        IntStream.range(0, times.size()).forEach(num->{
            maps.put(times.get(num), buttonList.get(num));// maps time to button
        });
        showButtonsInView(maps);// finally fetch buttons to the dashboard view
    }

    /**
     * Displays the buttons to the dashboard views.
     * @param maps the dictionary that has all the times mapped with the JButtons that user clicks
     */
    private void showButtonsInView(Map<Time, JButton> maps) {
        maps = new TreeMap<>(maps);// converts dictionary to time sorted times list Eg: 8 oclock comes before 9 oclock and so on
        maps.forEach((key, value)->{
            value.setFont(new MyCustomFont(8).getFont());//seting the font and
            value.setPreferredSize(new Dimension(10,10));//button size to all buttons in the dictionary
            dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().add(value);
        });
        dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().repaint();
        dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().validate();
        addButtonsSlotsAFunction(maps); //assigning buttons a function
        addSaveButtonAListener();//assigning a listener to button that will send data to database to save slots
    }


    /**
     * Saves the current slots chosen by customer to database.
     * It gets the selected slots felds and saves in database
     */
    private void addSaveButtonAListener() {
        dashboard.getSlotManagementPanel().getSaveButton().getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bsRep.addSlotsToDB(user.getId(), selectedSlots);// saving to database
                    Tools.alertMsg(dashboard,"You have successfully inserted the slots", "Success");
                    Log log = new Log(user.getId(), user.getEmail()+" has inserted available slots to the system");
                    Tools.recordALogToDB(log);//recording log
                    goToSlotsManagement();//reload page
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }


    /**
     * Assigns each slot button a listener to transter from left panel to right panel.
     * @param maps
     */
    private void addButtonsSlotsAFunction(Map<Time, JButton> maps) {
        /**
         * for each button in the dictionary
         */
        maps.forEach((key, value) -> value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyCustomDateAndTime dateAndTime = new MyCustomDateAndTime(Date.valueOf(dashboard
                        .getSlotManagementPanel().getCalendarPanel().getSelectedDate().toString()));
                dateAndTime = dateAndTime.withTime(Time.valueOf(e.getActionCommand()));
                //add to left panel
                dashboard.getSlotManagementPanel().getAvailableSlots().getContent().add(value);
                dashboard.getSlotManagementPanel().getAvailableSlots().getContent().validate();
                dashboard.getSlotManagementPanel().getAvailableSlots().getContent().repaint();
                dashboard.validadeAndRepaint();
                selectedSlots.add(dateAndTime);//also add to the field selectedSlots
            }
        }));
    }

    /**
     * returns all MyCustomDateAndTime objects in list to a List of just java.sql.Time
     * @param dateAndTimes
     * @return
     */
    private List<Time> createListOfTimes(List<MyCustomDateAndTime> dateAndTimes) {
        return dateAndTimes.stream().map(dateAndTime -> dateAndTime.getTime()).collect(Collectors.toList());
    }


    /**
     *Loads the UpcommingBookings page
     */
    private void goToUpComingBookings(){
        switchDashboardPanelToBookingList();
    }

    /**
     * Retrieves bookings from Database, process information and populate dashboard view with the results
     *
     */
    private void switchDashboardPanelToBookingList(){
        List<ManageBookingView> bookings = getAllBookings();// get All bookings from database
        /**
         * turning bookings list into 2D array
         */
        String[][] table = bookings.stream().map(booking->
                Arrays.asList(booking.getTimestamp()+"",booking.getCustomer().getFirstName(),
                        booking.getCustomer().getLastName(), booking.getBookingStatus()+"",
                        booking.getCustomer().getPhone().getPhone(), booking.getReview()+"")
                        .toArray(new String[6])).collect(Collectors.toList()).toArray(new String[bookings.size()][]);

        //creating JTable out of the 2D array generated above
        JTable jTable = new JTable(table, new String[]{"Date and Time", "Customer Name","Customer Last name","status","phone", "review"});
        //populate dashboard attribute with the table
        dashboard.withUpcomingBookingsPanel(jTable);
        //mapping row index to the ManageBookingView object
        Map<Integer, ManageBookingView> dictionary = mapTableWithManageBookingView(table, bookings);
        //adding listener to the new generated JTable
        addTableListener(jTable, dictionary);
        //clearing output, adding new Content and validating change, in other words, rendering view.
        dashboard.getOutput().removeAll();
        dashboard.getOutput().add(dashboard.getUpComingBookingsPanel());
        dashboard.validadeAndRepaint();
    }

    /**
     * Maps the table index to a ManageBookingView Object.
     * @param table
     * @param listOfBookings
     * @return
     */
    private Map<Integer, ManageBookingView> mapTableWithManageBookingView(String[][] table, List<ManageBookingView> listOfBookings) {
        Map<Integer, ManageBookingView> dictionary = new HashMap<>();
        IntStream.range(0, table.length).forEach(index-> dictionary.put(index, listOfBookings.get(index)));
        return dictionary;
    }


    /**
     * Adds a listener to the BookingTable
     * Service Providers can cancel bookings by clicking on the table row.
     * The booking to be cancelled is the one mapped to that row
     * @param table
     * @param dictionary
     */
    private void addTableListener(JTable table, Map<Integer, ManageBookingView> dictionary) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    Integer n = Tools.alertConfirm(dashboard, "Cancel Booking?");
                    //if answer is yes, cancel booking
                    if(n == 0){
                        try {
                            bRep.cancelBooking(dictionary.get(table.getSelectedRow()));
                            Log log = new Log(user.getId(), user.getEmail()+" cancelled a booking");
                            Tools.recordALogToDB(log);// record a log
                            goToUpComingBookings();// reload page
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * THis function uses the repository booking instance to retrieve a list of bookings from DB
     * @return
     */
    private List<ManageBookingView> getAllBookings(){
        return bRep.selectAllBookingsFromServiceProvider(user);
    }

    /**
     * Adding a listener to Logout JMenuItem
     */
    @Override
    public void addInputsAListener() {
        dashboard.getMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    /**
     * This function logs out the user by disposing of the window, setting user to null and recalling login
     */
    private void logout() {
        dashboard.dispose();
        app.setUser(null);
        app.login();
    }

}
