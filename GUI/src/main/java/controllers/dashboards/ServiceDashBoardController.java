package controllers.dashboards;

import controllers.Application;
import controllers.Control;
import models.entitiesRepresentation.ServiceProvider;
import models.repositories.BookingRepository;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
import models.utils.Tools;
import views.dashboard.serviceProvider.ServiceDashboard;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ServiceDashBoardController implements Control {

    private final ServiceDashboard dashboard;
    private final Application app;
    private final ServiceProvider user;
    private final BookingRepository bRep;

    public ServiceDashBoardController(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        dashboard = new ServiceDashboard();
        this.app = app;
        user = (ServiceProvider) app.getUser();

        setSideBarDashboardLayout();
        dashboard.getButtonsPanel().forEach(b->dashboard.getSideBar().add(b.getButton()));
        greetUser();

        addButtonsAFunction();
        addInputsAListener();

        bRep = new BookingRepository();
    }

    private void setSideBarDashboardLayout(){
        dashboard.getSideBar().setLayout(new GridLayout(20,1));
    }

    private void greetUser(){
        dashboard.getSideBar().setTitle("Hello, "+user.getCompanyFullName());
    }

    @Override
    public void addButtonsAFunction() {
        dashboard.getButtonsPanel().forEach(b->{
            b.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getActionCommand().contains("pcoming")){
//                        Tools.alertConfirm(dashboard, "upcomming");
                        try {
                            goToUpComingBookings();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else if (e.getActionCommand().contains("vailable")){
                        Tools.alertConfirm(dashboard, "available");
                    }else if (e.getActionCommand().contains("complete")){
                        Tools.alertConfirm(dashboard, "complete");
                    }
                }
            });
        });
    }

    private void goToUpComingBookings() throws SQLException {
        switchDashboardPanelToBookingList();
    }

    private void switchDashboardPanelToBookingList() throws SQLException {
        List<Tuple<TupleOf3Elements<String, String, String>, List<String>>> listOfBookings = getAllBookings();

        List<List<String>> tableAsList = Tools.brakeListOfTuplesToTuple_2(listOfBookings);

        String[][] table = Tools.convert2DlistTo2DArray(tableAsList);

        dashboard.withUpcomingBookingsPanel(table, new String[]{"Date and Time", "Customer Name", "Customer surname", "Customer phone", "Status"});

        addTableListener(dashboard.getUpComingBookingsPanel().getUpcomingBookingsTable(), listOfBookings);

        dashboard.getOutput().removeAll();
        dashboard.getOutput().add(dashboard.getUpComingBookingsPanel());
        dashboard.validadeAndRepaint();
    }

    private void addTableListener(JTable upcomingBookingsTable, List<Tuple<TupleOf3Elements<String, String, String>, List<String>>> listOfBookings) {
        upcomingBookingsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    TupleOf3Elements<String, String, String> id = listOfBookings.get(upcomingBookingsTable.getSelectedRow()).get_1();
                    Integer n = Tools.alertConfirm(dashboard, "Set it to Complete?");
                    if(n == 0){
                        try {
                            bRep.updateABookingStatus(id);
                            Tools.alertMsg(dashboard, "You have just updated a booking status\n" +
                                    "to Complete which no longer is an upcoming book so you won see it in this list",
                                    "Booking updated");
                            goToUpComingBookings();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private List<Tuple<TupleOf3Elements<String, String, String>, List<String>>> getAllBookings() throws SQLException {
        return bRep.selectAllBookingsFromServiceProvider(user.getId());
    }

    @Override
    public void addInputsAListener() {

    }
}
