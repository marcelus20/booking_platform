package controllers.dashboards;

import controllers.Application;
import controllers.Control;
import models.BookingStatus;
import models.Database;
import models.tuples.entitiesRepresentation.*;
import models.repositories.BookingRepository;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
import models.users.AbstraticUser;
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
import java.sql.Timestamp;
import java.util.List;


public class CustomerDashboardController implements Control {

    private Dashboard dashboard;
    private Application app;
    private AbstraticUser user;
    private UserTypes userType;
    private Database db;

    private Repository<BookingRepository> bRep;

    public enum UserTypes{
        ADMIN, CUSTOMER, SERVICE;
    }


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
            userType = UserTypes.ADMIN;

        }else if (user instanceof ServiceProvider){
            userType = UserTypes.SERVICE;
        }else{
            userType = UserTypes.CUSTOMER;
            dashboard = new CustomerDashboard();
        }
        displayMessage();
    }

    private void displayMessage(){
        String name = "";
        if(userType == UserTypes.SERVICE){
            name += ((ServiceProvider)user).getCompanyFullName();
        }else if (userType == UserTypes.CUSTOMER){
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
        if(UserTypes.CUSTOMER == userType){
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
                        String chosenCity = Tools.alertComboBox(dashboard, cities, "choose sity", "City search engine");
                        try {
                            mountTable(db.getListOfBarbersByCity(chosenCity));

                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

    }

    private void mountTable(List<List<String>> results){

        String[] tableColumns = {"id", "email ", "phone", "Provider Name","address", "city"};

        String[][] tableArray = (Tools.convert2DlistTo2DArray(results));

        JTable table = new JTable(tableArray, tableColumns);
        JScrollPane jScrollPane = new JScrollPane(table);
        ((CustomerDashboard) dashboard).getConsoleSearch()
                .getContainer().add(jScrollPane);

        dashboard.validadeAndRepaint();
        assignTableAListener(table);
    }

    private void assignTableAListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    String id = table.getValueAt(table.getSelectedRow(), 0).toString();

                    try {
                        selectServiceProvider(id);
                    } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectServiceProvider(String id) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Repository<ServiceProviderRepository> rep = new ServiceProviderRepository();

        ServiceProvider serviceProvider = Tools.filterServiceProviderToOnlyAvailableSlots((ServiceProvider) rep.selectObjById(id));
        ((CustomerDashboard)dashboard).setBookingPanel(new BookingPanel(serviceProvider));

        System.out.println("clicou");
        ((CustomerDashboard) dashboard).withOutput(((CustomerDashboard) dashboard).getBookingPanel());

        JTable table = ((CustomerDashboard) dashboard).getBookingPanel().getTable();



        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("It's been clicked");
                if(!e.getValueIsAdjusting()){


                    Tuple<String, String> id_ = Tuple.tuple("","");

                    id_ = id_.withX((String)table.getValueAt(table.getSelectedRow(), 0));
                    id_ = id_.withY((String)table.getValueAt(table.getSelectedRow(), 1));

                    TupleOf3Elements<String, String, String> bookingId = TupleOf3Elements
                            .tupleOf3Elements(id_.get_1(), user.getId() ,id_.get_2());

                    Integer n = Tools.alertConfirm(dashboard, "Do you want to book this available slot?");

                    Booking b = new Booking();
                    b.withBookingStatus(BookingStatus.PENDENT); b.withComplaint("");

                    serviceProvider.addBookingToSlot(b, Timestamp.valueOf(id_.get_1()));

                    if(n.equals(0)){
                        try {
                            ((BookingRepository)bRep).addBook(bookingId, serviceProvider.getBookingByTimestamp(Timestamp.valueOf(id_.get_1())));
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
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }else if(e.getActionCommand().contains("omplain")){//IF BUTTON IS MAKE A COMPLAINT
                    try {
                        goToComplaintPanel();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                addButtonsAFunction();//<-THIS THING WILL UPDATE THE LISTENER TO THE BUTTONS
            }
        });
    }

    private void goToComplaintPanel() throws SQLException {
        ((CustomerDashboard) dashboard).setConsoleSearch(new ConsoleSearch());

        ComplaintPanel complaintPanel = new ComplaintPanel();
//        complaintPanel.showComplaintContainer();

        List<Tuple<TupleOf3Elements<String, String, String>,List<String>>> shortenedListOfBookings =
                db.getShortenedListOfBookings(user.getId());


        List<List<String>> tableAsList = Tools.breakListOfTuplesToTuple_2(shortenedListOfBookings);


        String[][] table = Tools.convert2DlistTo2DArray(tableAsList);


        complaintPanel.withListofBookingPanel(table, new String[] {"Date And Time", "Barber/Hairdresser"});

        JTable t = complaintPanel.getTable();
        t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    complaintPanel.showComplaintContainer();
                    dashboard.validadeAndRepaint();

                    complaintPanel.getSubmit().getButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                ((BookingRepository)bRep)
                                        .updateAComplaint(shortenedListOfBookings.get(t
                                                .getSelectedRow()).get_1(),complaintPanel.getField().getText());
                                Tools.alertConfirm(dashboard, "Your Complaint has been submitted succesfully");
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            goToSearchEngine();
                        }
                    });
                }
            }
        });


        dashboard.validadeAndRepaint();
        ((CustomerDashboard) dashboard).setComplaintPanel(complaintPanel);

        ((CustomerDashboard)dashboard).withOutput(((CustomerDashboard) dashboard).getComplaintPanel());
    }

    private void goToSearchEngine(){
        ((CustomerDashboard) dashboard).setConsoleSearch(new ConsoleSearch());

        ((CustomerDashboard)dashboard).withOutput(((CustomerDashboard) dashboard).getConsoleSearch());

    }

    public void goToViewBookings() throws SQLException {
        ConsoleManageBookings consoleManageBookings = createConsoleManageBookings();
        ((CustomerDashboard)dashboard).withOutput(consoleManageBookings);
    }

    private ConsoleManageBookings createConsoleManageBookings() throws SQLException {

        List<Tuple<TupleOf3Elements<String, String, String>, List<String>>> bookings = ((BookingRepository)bRep).selectAllBookingsFromCustomer(user.getId());

        List<List<String>> listOfBookings = Tools.breakListOfTuplesToTuple_2(bookings);



        String[][] b = Tools.convert2DlistTo2DArray(listOfBookings);

        ConsoleManageBookings consoleManageBookings = new ConsoleManageBookings(b, new String[]{"Date and Time", "Hairdresser/Barber","Address", "City", "status" });

        JTable table = consoleManageBookings.getTableOfMyBookings();
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){

                    TupleOf3Elements id = bookings.get(table.getSelectedRow()).get_1();

                    Integer n = Tools.alertConfirm(dashboard,"Are you sure you want to cancel this booking?");

                    if(n.equals(0)){
                        try {
                            ((BookingRepository)bRep).cancelBooking(id);
                        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            goToViewBookings();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else{

                    }
                }
            }

        });

        return consoleManageBookings;
    }


    @Override
    public void addInputsAListener() {

    }
}
