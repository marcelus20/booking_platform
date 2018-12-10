package controllers.dashboards;

import controllers.Application;
import controllers.Control;
import models.Database;
import models.enums.BookingStatus;
import models.enums.UserType;
import models.repositories.*;
import models.tuples.entitiesRepresentation.*;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.joinedEntities.ManageBookingView;
import models.tuples.joinedEntities.ServiceProviderTableView;
import models.utils.Tools;
import views.customComponents.MyCustomJButton;
import views.dashboard.Dashboard;
import views.dashboard.customer.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class will be instantiated if user type is Customer.
 *
 * the class Application led code to the initialisation of this class.
 *
 * It implements the interface Control cause it will add inputs and buttons of the view classes
 * a functionality.
 */
public class CustomerDashboardController implements Control {

    /**
     * attributes:
     */
    private Dashboard dashboard; // the view dashboard for customers
    private Application app; // outter app instance to be controlled from this class
    private AbstraticUser user; // the user that has logged in
    private UserType userType; //the enumerator UserType instance for comparison
    private Repository<Booking> bRep;// an instance of bookingRepository for Booking DB table interaction
    private String selectedServiceId; // the id of service provider that user might be interacting with


    /**
     * Constructor: the Application class calls CustomerDashboardController and passes itself as parameter to this
     * constructor and populated the app attribute.
     * @param app
     */
    public CustomerDashboardController(Application app){
        this.app = app;
        user = app.getUser();
        bRep = new BookingRepository();
        initCurrentDashboardView();//rendering view
        addButtonsAFunction();//adding all buttons a functionality
        addInputsAListener();// adding all
    }

    /**
     * The redirector function to lead to the propper user type and display a message to user
     */
    public void initCurrentDashboardView(){
        if(user instanceof Admin){//IF USER IS ADMIN
            userType = UserType.ADMIN;

        }else if (user instanceof ServiceProvider){//IF USER IS SERVICE PROVIDER
            userType = UserType.SERVICE_PROVIDER;
        }else{// IF USER IS CUSTOMER
            userType = UserType.CUSTOMER;
            dashboard = new CustomerDashboard();
        }
        displayMessage();//GREET USER
    }

    /**
     * method for saying hello to the user
     */
    private void displayMessage(){
        String name = "";
        if(userType == UserType.SERVICE_PROVIDER){
            name += ((ServiceProvider)user).getCompanyFullName();// if user is service, get the companyFullName attribute
        }else if (userType == UserType.CUSTOMER){// if user is service, get the first name attribute
            name += ((Customer)user).getFirstName();
        }else{// if user is admin, get email
            name += user.getEmail();
        }
        //display message
        dashboard.getSideBar().setTitle("Hello, "+name+"! :)");
    }


    /**
     * Logout function
     */
    private void logout(){
        dashboard.dispose();// close the frame
        app.setUser(null);// set user to null
        app.setCustomerDashboardController(null);// set this class to null
        app.login();// redirect user to login.
    }

    /**
     * override method from Control interface
     */
    @Override
    public void addButtonsAFunction() {
        JMenuItem logout = dashboard.getMenuItem();//seting functionality to JMenuItem

        //on click of the logout JMenuItem add this listener
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //makes the logout if clicked
                logout();
            }
        });

        /**
         * setting up all buttons views contained in the dashboard.getButtonsPanel array.
         * Lopping through this array of button using the forEach lambda, it is being given functions to
         * the buttons using assgnFunctionToButton() method for each button.
         */
        if(UserType.CUSTOMER == userType){
            ((CustomerDashboard)dashboard).getButtonsPanel().forEach(b->assignFunctionToButton(b));
        }

        /**
         * Giving functionality to to the barbers search engine in the search console button.
         * This button will trigger a combobox with all of the cities so far registered in the database.
         * When user chooses the city, a list of all barbers from that city will pop up.
         */
        ((CustomerDashboard)dashboard).getConsoleSearch().getSearchByCity()
            .addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String [] cities;
                    try {
                        Repository<Location> locationRepository = new LocationRepository();
                        cities = locationRepository.getList(null).stream().map(location->location.getCity())
                                .collect(Collectors.toList()).toArray(new String[0]);
                        String chosenCity = Tools.alertComboBox(dashboard, cities, "choose city", "City search engine");
                        mountTable(getListOfBarbersByCity(chosenCity));

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
        });

    }

    /**
     * This function will be triggered when user is seaching for barbers and picks the city it wishes to book.
     * This will retrieve information from database searching all barbers from that city and return the List of
     * ServiceProvidersTableView class
     * @param city
     * @return the list Of servicesProvidersView that belongs to that city
     * @throws SQLException
     */
    private List<ServiceProviderTableView>getListOfBarbersByCity(String city){
        ServiceProviderRepository serviceProviderRepository = new ServiceProviderRepository();
        return serviceProviderRepository.getListOfServicesTableView(city);
    }

    /**
     * After the function above retrieve all services providers, the list will be passed as parameter to this function
     * and a JTable will be generated from the results
     * @param results : list of service providers
     */
    private void mountTable(List<ServiceProviderTableView> results){
        String[] tableColumns = {"id", "email ", "service name", "Address","city", "phone"};// array of columns of the JTable

        /**
         * the assignment bellow will turn the List results into a 2D array in order for the JTable to be created.
         * for each rows in the results list, the row will be turned into an array of strings. when all the rows
         * have been turned into arrays, they will be gathered in another array, resulting in a 2D String array
         */
        String[][] tableArray = results.stream().map(serviceProviderTableView ->//for each service
                Arrays.asList(serviceProviderTableView.getServiceId(),//put in the row array
                        serviceProviderTableView.getServiceEmail(),//put in the row array
                        serviceProviderTableView.getServiceName(),//put in the row array
                        serviceProviderTableView.getAddress(),//put in the row array
                        serviceProviderTableView.getCity(),//put in the row array
                        serviceProviderTableView.getPhone()).toArray(new String[6]))// close the row array
                .collect(Collectors.toList()).toArray(new String[results.size()][]);//include all rows arrays in a array (2D)
        //at this point the 2D array has been generated then can construct the JTable object
        JTable table = new JTable(tableArray, tableColumns);
        JScrollPane jScrollPane = new JScrollPane(table);// add JTable to JScrolPane
        ((CustomerDashboard) dashboard).getConsoleSearch()
                .getContainer().add(jScrollPane); // add JscrollPane to dashboard container
//
        dashboard.validadeAndRepaint();// validate and repaint
        //generate the dictionary - every line in the table will be mapped to a Service view object
        Map <Integer, ServiceProviderTableView> map = mapLineOfTableWithObject(table, results);
        assignTableAListener(table, map);// listen to the clicks of all table rows

    }

    /**
     * This function will map every row of the jtable to a corresponded ServiceProviderTableView object.
     * In other words, when user clicks on the line, the index of that line will identify which ServiceProviderTableView
     * user has been picked from the List
     * @param table  to retrieve the index of the selected row
     * @param results to retrieve the object at the index
     * @return the dictionary itself
     */
    private Map<Integer, ServiceProviderTableView> mapLineOfTableWithObject(JTable table, List<ServiceProviderTableView> results) {
        Map<Integer, ServiceProviderTableView> map = new HashMap<>();
        IntStream.range(0, table.getRowCount()).forEach(index->map.put(index, results.get(index)));
        return map;
    }

    /**
     * This function will listen to a click in the table selected row.
     * when clicked, the index of the row will be retrieved and the dictionary will reveal the value
     * correspondent to that index, which, in this case, is the correspondent ServiceProviderView
     * @param table to listen for a click
     * @param dictionary to map the line to its correspondent value
     */
    private void assignTableAListener(JTable table, Map<Integer, ServiceProviderTableView> dictionary) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){//after value has adjusted, this will prevent from triggering twice
                    Integer rowIndex = table.getSelectedRow(); // get id of row
                    System.out.println(dictionary.get(rowIndex));
                    ServiceProviderTableView selectedService = dictionary.get(rowIndex);// get the correspondent Service provider
                    selectBookingsFromProvider(selectedService);//download bookings from that service provider
                }
            }
        });
    }

    /**
     * using an instance of BookingSlotsRepository, this function will retreive all bookings belonged to a serviceProvider
     * object passed to the parameter.
     * @param serviceProviderTableView
     */
    private void selectBookingsFromProvider(ServiceProviderTableView serviceProviderTableView){
        try {
            Repository<BookingSlots> rep = new BookingSlotRepository(); // creating instance of BookingRepository
            List<BookingSlots> bookingSlots; // declaring list of bookingSlots
            //downloading the slots and filtering the resulting list to just available slots
            bookingSlots = Tools.filterBookingsToJustAvailables(((BookingSlotRepository) rep).getList(serviceProviderTableView.getServiceId()));
            //preparing view to receive the resulting list
            ((CustomerDashboard) dashboard).setBookingPanel(new BookingPanel(bookingSlots, serviceProviderTableView));
            ((CustomerDashboard) dashboard).withOutput(((CustomerDashboard) dashboard).getBookingPanel());
            //retrieving the JTable generated to map to a corresponding slot and add listener
            JTable table = ((CustomerDashboard) dashboard).getBookingPanel().getTable();
            //creating dictionary to map to the corresponding slot object
            Map<Integer, BookingSlots> map = mapLineOfTableWithBookingSlots(table, bookingSlots);
            //assigning table a listener
            addBookingTableAListener(table, map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating the dictionary to map table slots row to a correspondent booking slot.
     * @param table -> to retrieve index
     * @param bookingSlots to map index retrieved to find the booking slot from the list
     * @return the dictionary itself.
     */
    private Map<Integer, BookingSlots> mapLineOfTableWithBookingSlots(JTable table, List<BookingSlots> bookingSlots) {
        Map<Integer, BookingSlots> dictionary = new HashMap<>();
        IntStream.range(0, table.getRowCount()).forEach(index->dictionary.put(index, bookingSlots.get(index)));
        return dictionary;
    }

    /**
     *Adding listener to the booking slots table.
     * Same as the Service one, but this will map to a booking slot only.
     * @param table to listen for a click on the row
     * @param dictionary to find the slots at the table row index
     */
    private void addBookingTableAListener(JTable table, Map<Integer, BookingSlots> dictionary) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){//After adjusted, this will prevent from executing twice
                    final Integer rowIndex = table.getSelectedRow(); // get the index of the table row
                    BookingSlots chosenSlot = dictionary.get(rowIndex); // get the correspondent bookingSlot object
                    Integer n = Tools.alertConfirm(dashboard, "Do you want to book this available slot?"); // give user a choice
                    if(n.equals(0)){// if answer is yes, save the booking in the database
                        Repository<Log> logRepository = new LogRepository();
                        ((BookingRepository)bRep).addBook(chosenSlot, (Customer)user);//add to database the booking
                        //inform user that it transaction came through
                        Log log = new Log(user.getId(), ((Customer) user).getFirstName()+ "booked a slot!");
                        Tools.recordALogToDB(log); // add a log to database
                        goToViewBookings();//lead user to Bookings page
                    }
                }
            }
        });
    }


    /**
     * This function will assign every button of the sideBar dashboard for the Customer dashboard, a function
     * it is being called in the lambda annonymous function in the override method addButtonsAListener from Control
     * interface.
     * @param b the button from dashboard sideBar panel
     */
    private void assignFunctionToButton(MyCustomJButton b) {
        b.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().contains("earch")){ // IF BUTTON IS SEARCH FOR BARBERS
                    goToSearchEngine();
                }else if (e.getActionCommand().contains("bookings")){//IF BUTTONS IS MANAGING BOOKINGS
                    goToViewBookings();
                }else if(e.getActionCommand().contains("omplain")){//IF BUTTON IS MAKE A COMPLAINT
                    goToReviewPanel();
                }
                addButtonsAFunction();//<-THIS THING WILL UPDATE THE LISTENER TO THE BUTTONS
            }
        });
    }

    /**
     * This function will lead user to the Review/Complaint Panel after he/she has clicked on the button.
     * The static function Tools.generateArrayOfBookingReview will return an array of booking review, which is
     * an array of enums and will be use to populate a combo box for with the review options
     */
    private void goToReviewPanel(){
        ((CustomerDashboard) dashboard).setConsoleSearch(new ConsoleSearch());// adding console search to dashboard
        ComplaintPanel complaintPanel = new ComplaintPanel(Tools.generateArrayOfBookingReview());// retrieving array of options
        BookingRepository bookingRepository = new BookingRepository(); // loading the database booking agent
        //Downloading from DB the bookings from that user with COMPLETE status.
        List<ManageBookingView> shortenedListOfBookings =
                bookingRepository.generateBookingView(user, BookingStatus.COMPLETE);

        /**
         * turning the list of manageBookingView objects to a 2D array using map lambda List function.
         * For each row in the list, the rows will be transformed into a array, and at the end all row will be gathered in
         * an array too.
         */
        String[][] table =shortenedListOfBookings.stream().map(shortenedBooking->//for each shortbooking
                Arrays.asList(String.valueOf(shortenedBooking.getTimestamp()), shortenedBooking.getServiceId(),//put the attributes in an array
                        shortenedBooking.getCustomerId()).toArray(new String[3]))
                .collect(Collectors.toList()).toArray(new String[shortenedListOfBookings.size()][]);//gather all rows in an array
        //create the JTable
        JTable t = new JTable(table, new String[]{"Date and Time", "Service", "Review"});
        //map each line of the table to a ManageBookingView Object
        Map<Integer, ManageBookingView> dictionary = mapLineOfTableWithManageBookingView(t, shortenedListOfBookings);
        // add a listener to the table rows
        complaintPanel.withListofBookingPanel(t);
        t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){//after value has adjusted, this will prevent from executing twice
                    complaintPanel.showComplaintContainer();//toggle the container in the view
                    dashboard.validadeAndRepaint();// validate and repaint
                    selectedServiceId = dictionary.get(t.getSelectedRow()).getServiceId();// get the id of service and populate global variable
                    giveSubimitReviewALIstener(complaintPanel, dictionary.get(t.getSelectedRow()));//give a listener to the submit button
                }
            }
        });
        /**
         * now is for the complaint wise
         */
        dashboard.validadeAndRepaint();
        ((CustomerDashboard) dashboard).setComplaintPanel(complaintPanel);
        //mount the complaint panel
        ((CustomerDashboard)dashboard).withOutput(((CustomerDashboard) dashboard).getComplaintPanel());
        //add the button "send a complaint" a lister
        complaintPanel.getSendComplaint().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    recordAComplaint(complaintPanel.getTextArea());// add the text written by customer to the db
                    //getting name of barber to compose the message
                    String serviceName = new ServiceProviderRepository().selectObjById(selectedServiceId).getCompanyFullName();
                    //add log to db
                    Tools.alertMsg(dashboard, "You have successfully added a complaint to "+ serviceName, "success");
                    Log log = new Log(user.getId(), ((Customer) user).getFirstName()+ "Made a complaint about "+serviceName);
                    Tools.recordALogToDB(log);
                    goToSearchEngine();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * Adds the new complaint to the database. it will get the string from the textarea, id of service provider
     * and id of the customer itself for the complaint recording in the database.
     * @param complaintPanelTextArea -> the complaint
     * @throws SQLException
     */

    private void recordAComplaint(String complaintPanelTextArea) throws SQLException {
        Repository<Complaint> complaintRepository = new ComplaintRepository(); // loading the object that talks to DB
        Complaint complaint = new Complaint();//creating the complaint model object
        complaint.setServiceId(selectedServiceId);//populating complaint with the service id
        complaint.setCustomerId(user.getId());//populating object with user id
        complaint.setComplaint(complaintPanelTextArea); // populating obj with the complaint string text
        complaintRepository.addToDB(complaint); // adding to database this new tuple entry.
    }

    /**
     * Gives a listener to the review submition button. When clicked, it will save the review selected in the
     * JCombobox by user and update the booking table in the DB with the review
     * @param complaintPanel the view complaint object
     * @param manageBookingView the booking view object
     */
    private void giveSubimitReviewALIstener(ComplaintPanel complaintPanel, ManageBookingView manageBookingView) {
        complaintPanel.getSubmit().getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // update review in the database with the bookings information.
                    ((BookingRepository)bRep).updateAReview(manageBookingView,String.valueOf(complaintPanel
                                            .getBookingReviewComboBox().getSelectedItem()));
                    //tell user of the success
                    Tools.alertConfirm(dashboard, "Your review has been successfuly submitted");
                    //log operation to DB
                    Log log = new Log(user.getId(), ((Customer) user).getFirstName()+ "Submitted a review");
                    Tools.recordALogToDB(log);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                //lead user to the search engine page.
                goToSearchEngine();
            }
        });
    }

    /**
     * Leads user to the search Barbers engine page after the button has been clicked in the dashboard side bar
     */
    private void goToSearchEngine(){
        ((CustomerDashboard) dashboard).setConsoleSearch(new ConsoleSearch()); // initialise console seach
        //add to the output panel the search console.
        ((CustomerDashboard)dashboard).withOutput(((CustomerDashboard) dashboard).getConsoleSearch());
    }

    /**
     * Leads user to the bookings page after clicked on the button on the side bar dashboard panel
     */
    public void goToViewBookings(){
        ConsoleManageBookings consoleManageBookings = createConsoleManageBookings(); // initalises the view
        ((CustomerDashboard)dashboard).withOutput(consoleManageBookings); // add view to the panel
    }

    /**
     * Displays the bookings with the barbers that the customer user has booked.
     * It downloads from DB the bookings and mount the view dashboard to display the table of bookings.
     * @return
     */
    private ConsoleManageBookings createConsoleManageBookings() {
        BookingRepository bookingRepository = new BookingRepository();//loading the db agent
        List<ManageBookingView> manageBookingViewList; // declaring list of the bookings
        manageBookingViewList = bookingRepository.generateBookingView(user, null);//downloading all bookings belonged to that user
        /**
         * turn list of bookings into a 2D array for the generation of the JTable
         */
        String[][] table = manageBookingViewList.stream().map(booking->
                Arrays.asList(String.valueOf(booking.getTimestamp()), booking.getCompanyName(), String.valueOf(booking.getBookingStatus()),
                        booking.getPhone(), String.valueOf(booking.getReview())).toArray(new String[5]))
                .collect(Collectors.toList()).toArray(new String[manageBookingViewList.size()][]);//Tools.mapManageBookingCustomerViewsListToArray(manageBookingViewList);

        //creating JTable
        JTable jTable = new JTable(table, new String[]{"Date and Time", "Hairdresser/Barber", "Status", "phone", "review"});
        //populating consoleManageBookings with the table
        ConsoleManageBookings consoleManageBookings = new ConsoleManageBookings(jTable);
        // mapping table rows (index) to a correspondent booking object
        Map<Integer, ManageBookingView> dictionary = mapLineOfTableWithManageBookingView(jTable, manageBookingViewList);
        //giving table a listener
        giveTableOfBookingAListener(jTable, dictionary);
        dashboard.validadeAndRepaint();// after mounted view, update and repaint.
        return consoleManageBookings;
    }


    /**
     * Gives each row of the table of manageBookingsview a listener.
     * it retrives the index of the row and get the correspondent object using the dictionary
     * @param jTable
     * @param dictionary
     */
    private void giveTableOfBookingAListener(JTable jTable, Map<Integer, ManageBookingView> dictionary) {
        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){//after it has adjusted, this will prevent from executing twice
                    final Integer n = Tools.alertConfirm(dashboard, "Do you want to cancel this booking?");
                    if(n == 0){// if yes, user wishes to cancel bookings
                        try {
                            //canceling booking form database
                            ((BookingRepository) bRep).cancelBooking(dictionary.get(jTable.getSelectedRow()));
                            //recording the log
                            Log log = new Log(user.getId(), ((Customer) user).getFirstName()+ "Cancelled the booking");
                            Tools.recordALogToDB(log);
                            //lead user to view bookings page
                            goToViewBookings();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * creates a dictionary mapping an Integer index to an ManageBookingView Object.
     * @param jTable
     * @param manageBookingViewList
     * @return
     */
    private Map<Integer, ManageBookingView> mapLineOfTableWithManageBookingView(JTable jTable, List<ManageBookingView> manageBookingViewList) {
        Map<Integer, ManageBookingView> map = new HashMap<>();
        IntStream.range(0,jTable.getRowCount()).forEach(index->map.put(index, manageBookingViewList.get(index)));
        return map;
    }

    @Override
    public void addInputsAListener() {

    }
}
