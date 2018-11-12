package controllers.dashboards;

import controllers.Application;
import controllers.Control;
import models.Database;
import models.entitiesRepresentation.*;
import models.repositories.BookingRepository;
import models.repositories.BookingSlotRepository;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
import models.users.AbstraticUser;
import models.utils.Tools;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;
import views.dashboard.Dashboard;
import views.dashboard.customer.BookingPanel;
import views.dashboard.customer.ConsoleManageBookings;
import views.dashboard.customer.ConsoleSearch;
import views.dashboard.customer.CustomerDashboard;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


public class DashboardController implements Control {

    private Dashboard dashboard;
    private Application app;
    private AbstraticUser user;
    private UserTypes userType;
    private Database db;

    private Repository<BookingRepository> bRep;

    public enum UserTypes{
        ADMIN, CUSTOMER, SERVICE;
    }


    public DashboardController(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
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
        app.setDashboardController(null);
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
                        String chosenCity = Tools.alertComboBox(dashboard, cities);
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
                    b.withBookingStatus("NOT COMPLETE"); b.withComplaint("");

                    serviceProvider.addBookingToSlot(b, Timestamp.valueOf(id_.get_1()));

                    if(n.equals(0)){
                        try {
                            ((BookingRepository)bRep).addBook(bookingId, serviceProvider.getBookingByTimestamp(Timestamp.valueOf(id_.get_1())));
                            goToViewBookings();
                        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }
                    System.out.println(n);

                }
            }
        });



    }

    private void assignFunctionToButton(MyCustomJButton b) {
        b.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().contains("earch")){
                    goToSearchEngine();
                }else if (e.getActionCommand().contains("bookings")){
                    try {
                        goToViewBookings();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                addButtonsAFunction();//<-THIS THING WILL UPDATE THE LISTENER TO THE BUTTONS
            }
        });
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

        List<Tuple<TupleOf3Elements<String, String, String>, List<String>>> bookings = ((BookingRepository)bRep).selectAllBookings(user.getId());

        List<List<String>> listOfBookings = Tools.brakeListOfTuplesToTuple_2(bookings);



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
