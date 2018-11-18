package controllers.dashboards;

import controllers.Application;
import controllers.Control;
import models.Database;
import models.enums.UserType;
import models.repositories.BookingSlotRepository;
import models.tuples.entitiesRepresentation.*;
import models.repositories.BookingRepository;
import models.repositories.Repository;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class CustomerDashboardController implements Control {

    private Dashboard dashboard;
    private Application app;
    private AbstraticUser user;
    private UserType userType;
    private Database db;

    private Repository<Booking> bRep;




    public CustomerDashboardController(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.app = app;
        user = app.getUser();
        bRep = new BookingRepository();
        try {
            db = new Database();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        initCurrentDashboardView();
        buildProperPanel();


        addButtonsAFunction();
        addInputsAListener();
    }

    public void initCurrentDashboardView(){
        if(user instanceof Admin){
            userType = UserType.ADMIN;

        }else if (user instanceof ServiceProvider){
            userType = UserType.SERVICE_PROVIDER;
        }else{
            userType = UserType.CUSTOMER;
            dashboard = new CustomerDashboard();
        }
        displayMessage();
    }

    private void displayMessage(){
        String name = "";
        if(userType == UserType.SERVICE_PROVIDER){
            name += ((ServiceProvider)user).getCompanyFullName();
        }else if (userType == UserType.CUSTOMER){
            name += ((Customer)user).getFirstName();
        }else{
            name += user.getEmail();
        }
        dashboard.getSideBar().setTitle("Hello, "+name+"! :)");
    }

    private void buildProperPanel(){
    }

    private void logout(){
        dashboard.dispose();
        app.setUser(null);
        app.setCustomerDashboardController(null);
        app.login();
    }

    @Override
    public void addButtonsAFunction() {
        JMenuItem logout = dashboard.getMenuItem();

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        if(UserType.CUSTOMER == userType){
            ((CustomerDashboard)dashboard).getButtonsPanel().forEach(b->assignFunctionToButton(b));
        }

        ((CustomerDashboard)dashboard).getConsoleSearch().getSearchByCity()
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String [] cities = null;
                        try {
                            cities = db.getListOfCities().toArray(new String[0]);

                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        String chosenCity = Tools.alertComboBox(dashboard, cities, "choose city", "City search engine");
                        try {
                            mountTable(db.getListOfBarbersByCity(chosenCity));

                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

    }

    private void mountTable(List<ServiceProviderTableView> results){

        String[] tableColumns = {"id", "email ", "service name", "Address","city", "phone"};

//        Map<List<String>, ServiceProviderTableView> map = Tools.convertArrayOfBarberTableViewToListOfString(results);

        String[][] tableArray = (Tools.convert2DlistTo2DArray(results));

        JTable table = new JTable(tableArray, tableColumns);
        JScrollPane jScrollPane = new JScrollPane(table);
        ((CustomerDashboard) dashboard).getConsoleSearch()
                .getContainer().add(jScrollPane);
//
        dashboard.validadeAndRepaint();
        Map <Integer, ServiceProviderTableView> map = mapLineOfTableWithObject(table, results);
        assignTableAListener(table, map);

    }

    private Map<Integer, ServiceProviderTableView> mapLineOfTableWithObject(JTable table, List<ServiceProviderTableView> results) {
        Map<Integer, ServiceProviderTableView> map = new HashMap<>();
        IntStream.range(0, table.getRowCount()).forEach(index->map.put(index, results.get(index)));
        return map;
    }

    private void assignTableAListener(JTable table, Map<Integer, ServiceProviderTableView> dictionary) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){

                    Integer rowIndex = table.getSelectedRow();

                    System.out.println(dictionary.get(rowIndex));

                    ServiceProviderTableView selectedService = dictionary.get(rowIndex);


                    try {
                        selectBookingsFromProvider(selectedService);
                    } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectBookingsFromProvider(ServiceProviderTableView serviceProviderTableView) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Repository<BookingSlots> rep = new BookingSlotRepository();

        List<BookingSlots> bookingSlots = Tools.filterBookingsToJustAvailables(((BookingSlotRepository) rep).getList(serviceProviderTableView.getServiceId()));

        ((CustomerDashboard) dashboard).setBookingPanel(new BookingPanel(bookingSlots, serviceProviderTableView));


        ((CustomerDashboard) dashboard).withOutput(((CustomerDashboard) dashboard).getBookingPanel());

        JTable table = ((CustomerDashboard) dashboard).getBookingPanel().getTable();
        Map<Integer, BookingSlots> map = mapLineOfTableWithBookingSlots(table, bookingSlots);
        addBookingTableAListener(table, map);
    }

    private Map<Integer, BookingSlots> mapLineOfTableWithBookingSlots(JTable table, List<BookingSlots> bookingSlots) {
        Map<Integer, BookingSlots> dictionary = new HashMap<>();
        IntStream.range(0, table.getRowCount()).forEach(index->dictionary.put(index, bookingSlots.get(index)));
        return dictionary;
    }

    private void addBookingTableAListener(JTable table, Map<Integer, BookingSlots> dictionary) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){

                    final Integer rowIndex = table.getSelectedRow();

                    BookingSlots chosenSlot = dictionary.get(rowIndex);

                    Integer n = Tools.alertConfirm(dashboard, "Do you want to book this available slot?");

                    if(n.equals(0)){
                        try {
                            ((BookingRepository)bRep).addBook(chosenSlot, (Customer)user);
                            goToViewBookings();
                        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    private void assignFunctionToButton(MyCustomJButton b) {
        b.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().contains("earch")){ // IF BUTTON IS SEARCH FOR BARBERS
                    goToSearchEngine();
                }else if (e.getActionCommand().contains("bookings")){//IF BUTTONS IS MANAGING BOOKINGS
                    try {
                        goToViewBookings();
                    } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }else if(e.getActionCommand().contains("omplain")){//IF BUTTON IS MAKE A COMPLAINT
                    try {
                        goToReviewPanel();
                    } catch (SQLException | InstantiationException | ClassNotFoundException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
                addButtonsAFunction();//<-THIS THING WILL UPDATE THE LISTENER TO THE BUTTONS
            }
        });
    }

    private void goToReviewPanel() throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ((CustomerDashboard) dashboard).setConsoleSearch(new ConsoleSearch());

        ComplaintPanel complaintPanel = new ComplaintPanel(Tools.generateArrayOfBookingReview());

        List<ManageBookingView> shortenedListOfBookings =
                db.generateBookingView(user, null);

        String[][] table = Tools.mapManageBookingViewsListToArrayShortened(shortenedListOfBookings);

        JTable t = new JTable(table, new String[]{"Date and Time", "Service", "Review"});

        Map<Integer, ManageBookingView> dictionary = mapLineOfTableWithManageBookingView(t, shortenedListOfBookings);
        complaintPanel.withListofBookingPanel(t);

        t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    complaintPanel.showComplaintContainer();
                    dashboard.validadeAndRepaint();
                    giveSubmitComplaintAListener(complaintPanel, dictionary.get(t.getSelectedRow()));

                }
            }
        });


        dashboard.validadeAndRepaint();
        ((CustomerDashboard) dashboard).setComplaintPanel(complaintPanel);

        ((CustomerDashboard)dashboard).withOutput(((CustomerDashboard) dashboard).getComplaintPanel());
    }

    private void giveSubmitComplaintAListener(ComplaintPanel complaintPanel, ManageBookingView manageBookingView) {
        complaintPanel.getSubmit().getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ((BookingRepository)bRep)
                            .updateAReview(manageBookingView,
                                    String.valueOf(complaintPanel.getBookingReviewComboBox().getSelectedItem()));
                    Tools.alertConfirm(dashboard, "Your review has been successfuly submitted");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                goToSearchEngine();
            }
        });
    }

    private void goToSearchEngine(){
        ((CustomerDashboard) dashboard).setConsoleSearch(new ConsoleSearch());

        ((CustomerDashboard)dashboard).withOutput(((CustomerDashboard) dashboard).getConsoleSearch());

    }

    public void goToViewBookings() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        ConsoleManageBookings consoleManageBookings = createConsoleManageBookings();
        ((CustomerDashboard)dashboard).withOutput(consoleManageBookings);
    }

    private ConsoleManageBookings createConsoleManageBookings() throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {

        List<ManageBookingView> manageBookingViewList = db.generateBookingView(user, null);

        String[][] table = Tools.mapManageBookingCustomerViewsListToArray(manageBookingViewList);

        JTable jTable = new JTable(table, new String[]{"Date and Time", "Hairdresser/Barber", "Status", "phone", "review"});

        ConsoleManageBookings consoleManageBookings = new ConsoleManageBookings(jTable);

        Map<Integer, ManageBookingView> dictionary = mapLineOfTableWithManageBookingView(jTable, manageBookingViewList);
        giveTableOfBookingAListener(jTable, dictionary);
        dashboard.validadeAndRepaint();
        return consoleManageBookings;
    }

    private void giveTableOfBookingAListener(JTable jTable, Map<Integer, ManageBookingView> dictionary) {
        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    final Integer n = Tools.alertConfirm(dashboard, "Do you want to cancel this booking?");

                    if(n == 0){
                        try {
                            ((BookingRepository) bRep).cancelBooking(dictionary.get(jTable.getSelectedRow()));
                            goToViewBookings();
                        } catch (SQLException | IllegalAccessException | ClassNotFoundException | InstantiationException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private Map<Integer, ManageBookingView> mapLineOfTableWithManageBookingView(JTable jTable, List<ManageBookingView> manageBookingViewList) {
        Map<Integer, ManageBookingView> map = new HashMap<>();
        IntStream.range(0,jTable.getRowCount()).forEach(index->map.put(index, manageBookingViewList.get(index)));
        return map;
    }

    @Override
    public void addInputsAListener() {

    }
}
