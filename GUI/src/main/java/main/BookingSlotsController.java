package main;

import com.github.lgooddatepicker.components.*;

import com.github.lgooddatepicker.optionalusertools.CalendarSelectionListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import controllers.controllers.Controlls;
import utils.Tools;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.List;

public class BookingSlotsController extends JFrame implements Controlls<Slots> {
    private Slots slots;
    private Slots availableSlots;
    private SlotOptions slotOptions;


    private List<JButton> slotsButtons;
    private List<JButton> availableSlotsButtons;
    private Tools.MyCustomDateTime dateTime;
    private CalendarPanel calendarPanel;
    private List<Tools.MyCustomDateTime> dateTimes;


    public BookingSlotsController() {
        slots = new Slots("Slots");
        availableSlots = new Slots("AvailableSlots");
        dateTime = new Tools.MyCustomDateTime();
        slotOptions = new SlotOptions();
        calendarPanel = new CalendarPanel();
        dateTimes = new ArrayList<>();

        config();
        setSizes();
        build();
    }

    @Override
    public void config() {
        addCalendarPanelAListener();
        addAddDateToScheduleButtonAListener();
        availableSlotsButtons = new ArrayList<>();
        slotsButtons = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            slotsButtons.add(new JButton(String.valueOf(dateTime.getTime())));
            dateTime = dateTime.add30();
        }

        slotsButtons.forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(availableSlotsButtons.size() != 0 && calendarPanel.getSelectedDate() != null){
                        slotOptions.getAddDayToSchedule().setEnabled(true);
                    }
                    JButton newButton = new JButton(button.getText());
                    availableSlotsButtons.add(newButton);
                    availableSlotsButtons = Tools.sortJButtonsByText(availableSlotsButtons);

                    button.setEnabled(false);


                    availableSlotsButtons.forEach(b->{
                        availableSlots.getButtonPanel().removeAll();
                        b.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try{
                                    availableSlotsButtons.forEach(bu->{
                                        if(bu.getText().equals(e.getActionCommand())){
                                            availableSlotsButtons.remove(b);
                                        }
                                    });
                                }catch (Exception ex){}

                                slotsButtons.forEach(bu->{
                                    if(bu.getText().equals(b.getText())){
                                        bu.setEnabled(true);
                                        b.setEnabled(false);
                                    }
                                });

                            }
                        });
                    });

                    availableSlots.getButtonPanel().removeAll();
                    availableSlotsButtons.forEach(b->availableSlots.getButtonPanel().add(b));


                    validateAndRepaintAll();
                }
            });

            slots.getButtonPanel().add(button);

        });

        slotOptions.getAddDayToSchedule().setEnabled(false);

        setSize(500,500);
        setVisible(true);
        validateAndRepaintAll();
    }

    private void addTextDateToLabel(String txt) {
        slotOptions.setAddedDatesLabel(txt);
    }

    @Override
    public void build() {
        slots.getButtonPanel().setLayout(new GridLayout(0,4));
        slots.setLayout(new BorderLayout());

        availableSlots.getButtonPanel().setLayout(new GridLayout(0,4));
        availableSlots.setLayout(new BorderLayout());

        slots.add(slots.getTitle(), BorderLayout.NORTH);
        slots.add(slots.getButtonPanel(), BorderLayout.CENTER);

        availableSlots.add(availableSlots.getTitle(), BorderLayout.NORTH);
        availableSlots.add(availableSlots.getButtonPanel(), BorderLayout.SOUTH);

        slotOptions.setLayout(new BorderLayout());

        slotOptions.getTitle().add(new JLabel("BOOKING SLOTS SCHEDULING"));
        slotOptions.add(slotOptions.getFooter(), BorderLayout.SOUTH);

        slotOptions.getFooter().add(slotOptions.getSave());
        slotOptions.add(slotOptions.getFooter(), BorderLayout.SOUTH);

        slotOptions.getOutput().setLayout( new GridLayout(2,1));
        slotOptions.getOutput().add(slots); slotOptions.getOutput().add(availableSlots);
        slotOptions.add(slotOptions.getOutput(), BorderLayout.LINE_END);

        slotOptions.getSideBar().setLayout(new BorderLayout());
        slotOptions.getSideBar().add(calendarPanel, BorderLayout.NORTH);
        slotOptions.getSideBar().add(slotOptions.getAddedDates(), BorderLayout.CENTER);
        slotOptions.getAddedDates().add(slotOptions.getAddedDatesLabel());
        slotOptions.getSideBar().add(slotOptions.getAddDayToSchedule(), BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(slotOptions.getSideBar());
        slotOptions.add(scrollPane, BorderLayout.LINE_START);
        add(slotOptions);
    }

    @Override
    public void setSizes() {

    }

    private void validateAndRepaintAll(){
        slots.repaint();
        availableSlots.repaint();
        availableSlots.validate();
        availableSlots.validate();
        repaint();
        validate();
    }

    private void addCalendarPanelAListener(){
        calendarPanel.addCalendarSelectionListener(new CalendarSelectionListener() {
            @Override
            public void selectionChanged(CalendarSelectionEvent calendarSelectionEvent) {
                if(availableSlotsButtons.size() != 0){
                    slotOptions.getAddDayToSchedule().setEnabled(true);
                }

            }
        });
    }

    private void addAddDateToScheduleButtonAListener(){
        slotOptions.getAddDayToSchedule().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slotOptions.getAddDayToSchedule().setEnabled(false);
                addTextDateToLabel(String.valueOf(calendarPanel.getSelectedDate()));

                availableSlotsButtons
                        .forEach(b->{
                            Tools.MyCustomDateTime dateTime = new Tools.MyCustomDateTime(String.valueOf(calendarPanel.
                                    getSelectedDate()), b.getText());
                            System.out.println(dateTimes.contains(dateTime));
                            if (!dateTimes.contains(dateTime)){
                                dateTimes.add(dateTime);
                            }
                        });
                validateAndRepaintAll();
                dateTimes.forEach(d-> System.out.println(d.getTimestamp()));

            }
        });
    }

    private void addSaveAListener(){
        slotOptions.getSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
