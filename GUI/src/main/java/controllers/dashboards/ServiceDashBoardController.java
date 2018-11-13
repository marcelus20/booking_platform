package controllers.dashboards;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.CalendarSelectionListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import controllers.Application;
import controllers.Control;
import models.tuples.entitiesRepresentation.ServiceProvider;
import models.repositories.BookingRepository;
import models.repositories.BookingSlotRepository;
import models.tuples.Tuple;
import models.tuples.TupleOf3Elements;
import models.utils.MyCustomDateAndTime;
import models.utils.Tools;
import views.customComponents.MyCustomFont;
import views.customComponents.MyCustomJButton;
import views.dashboard.serviceProvider.ServiceDashboard;
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

public class ServiceDashBoardController implements Control {

    private final ServiceDashboard dashboard;
    private final Application app;
    private final ServiceProvider user;
    private final BookingRepository bRep;
    private final BookingSlotRepository bsRep;

    private final List<MyCustomDateAndTime> selectedSlots;

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
        selectedSlots = new ArrayList<>();
        bsRep = new BookingSlotRepository();
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
//                        Tools.alertConfirm(dashboard, "available");
                        goToSlotsManagement();
                    }else if (e.getActionCommand().contains("complete")){
                        Tools.alertConfirm(dashboard, "complete");
                    }
                }
            });
        });

    }


    private void goToSlotsManagement() {
        switchDashboardPanelToSlotsManagement();
    }

    private void switchDashboardPanelToSlotsManagement() {
        dashboard.getOutput().removeAll();
        dashboard.withSlotsManagementPanel(new SlotManagementPanel());
        dashboard.getOutput().add(dashboard.getSlotManagementPanel());
        dashboard.validadeAndRepaint();
        addListenerToCalendarPanel(dashboard.getSlotManagementPanel().getCalendarPanel());
    }

    private void addListenerToCalendarPanel(CalendarPanel calendarPanel) {
        calendarPanel.addCalendarSelectionListener(new CalendarSelectionListener() {
            @Override
            public void selectionChanged(CalendarSelectionEvent calendarSelectionEvent) {
                setOutputSlotsTitle(calendarPanel.getSelectedDate());
                toggleSlotsPanel(calendarSelectionEvent.getNewDate());
            }
        });
    }

    private void setOutputSlotsTitle(LocalDate selectedDate) {
        dashboard.getSlotManagementPanel().getSlotOutputPanel().setTitle("Slots for the Date of "+ selectedDate);
    }

    private void toggleSlotsPanel(LocalDate newDate) {
        dashboard.getSlotManagementPanel().getContent()
                .add(dashboard.getSlotManagementPanel().getSlotOutputPanel());
        dashboard.validadeAndRepaint();
        clearSlotsPanels();
        clearSelectedSlotsField();
        createSlotsDateAndTime(dashboard.getSlotManagementPanel().getCalendarPanel().getSelectedDate());
    }

    private void clearSelectedSlotsField() {
        selectedSlots.clear();
    }

    private void clearSlotsPanels() {
        dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().removeAll();
        dashboard.getSlotManagementPanel().getAvailableSlots().getContent().removeAll();
    }

    private void createSlotsDateAndTime(LocalDate selectedDate) {
        MyCustomDateAndTime d = new MyCustomDateAndTime(Date.valueOf(selectedDate));
        List<MyCustomDateAndTime> dateAndTimes = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            dateAndTimes.add(d);
            d = d.add30MinutesToTime();
        }
        createSlotsButtons(dateAndTimes);
    }

    private void createSlotsButtons(List<MyCustomDateAndTime> dateAndTimes) {
        List<JButton> buttonList = dateAndTimes.stream()
                .map(dateAndTime -> new MyCustomJButton(String.valueOf(dateAndTime.getTime()))
                        .getButton()).collect(Collectors.toList());
        List<Time> times = createListOfTimes(dateAndTimes);
        generateDictionary(times, buttonList);
    }

    private void generateDictionary(List<Time> times, List<JButton> buttonList) {
        Map<Time,JButton> maps = new HashMap<>();
        IntStream.range(0, times.size()).forEach(num->{
            maps.put(times.get(num), buttonList.get(num));
        });
        showButtonsInView(maps);
    }

    private void showButtonsInView(Map<Time, JButton> maps) {
        maps = new TreeMap<>(maps);
        maps.forEach((key, value)->{
            value.setFont(new MyCustomFont(8).getFont());
            value.setPreferredSize(new Dimension(10,10));
            dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().add(value);
        });
        dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().repaint();
        dashboard.getSlotManagementPanel().getNotAvailableSlots().getContent().validate();
        addButtonsSlotsAFunction(maps);
        addSaveButtonAListener();
    }

    private void addSaveButtonAListener() {
        dashboard.getSlotManagementPanel().getSaveButton().getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bsRep.addSlotsToDB(user.getId(), selectedSlots);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                Tools.alertMsg(dashboard,"You have successfully inserted the slots", "Success");
                goToSlotsManagement();
            }
        });

    }

    private void addButtonsSlotsAFunction(Map<Time, JButton> maps) {

        maps.forEach((key, value) -> value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyCustomDateAndTime dateAndTime = new MyCustomDateAndTime(Date.valueOf(dashboard
                        .getSlotManagementPanel().getCalendarPanel().getSelectedDate().toString()));
                dateAndTime = dateAndTime.withTime(Time.valueOf(e.getActionCommand()));
                dashboard.getSlotManagementPanel().getAvailableSlots().getContent().add(value);
                dashboard.getSlotManagementPanel().getAvailableSlots().getContent().validate();
                dashboard.getSlotManagementPanel().getAvailableSlots().getContent().repaint();
                dashboard.validadeAndRepaint();

                selectedSlots.add(dateAndTime);
            }
        }));
    }

    private List<Time> createListOfTimes(List<MyCustomDateAndTime> dateAndTimes) {
        return dateAndTimes.stream().map(dateAndTime -> dateAndTime.getTime()).collect(Collectors.toList());
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
