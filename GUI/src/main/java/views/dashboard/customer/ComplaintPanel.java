package views.dashboard.customer;

import models.enums.BookingReview;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class ComplaintPanel extends MyCustomJPanel {

    private JTable table;
    private final MyCustomJPanel listOfBookingsPanel;
    private final MyCustomJPanel complaintContainer;
    private final MyCustomJButton submit;
    private final JComboBox<BookingReview> bookingReviewComboBox;
    private final MyCustomJPanel complaintManager;
    private final JTextArea textArea;
    private final MyCustomJButton sendComplaint;

    public ComplaintPanel(BookingReview[] options) {
        super("Give a feedback about the service that you have experienced", 750, 700);
        submit = new MyCustomJButton("Submit", 100,40);
        bookingReviewComboBox = new JComboBox<BookingReview>(options);
//        bookingReviewComboBox.setPreferredSize(new Dimension(300,300));
        listOfBookingsPanel = new MyCustomJPanel("List Of Bookings", 300, 10);
        complaintContainer = new MyCustomJPanel("Action Manager", 300, 10);
        complaintManager = new MyCustomJPanel("Complaints", 250, 250);
        sendComplaint =  new MyCustomJButton("Send Complaint", 200,50);
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(250,100));
        complaintManager.getContent().setLayout(new GridLayout(2,1));
        complaintManager.getContent().add(textArea);
        complaintManager.getContent().add(sendComplaint.getButton());
        getContent().setLayout(new BorderLayout());
        getContent().add(listOfBookingsPanel, BorderLayout.LINE_START);



    }

    public void showComplaintContainer(){
        complaintContainer.getContent().setLayout(new GridLayout(5,1));
        complaintContainer.getContent().add(bookingReviewComboBox);
        complaintContainer.getContent().add(submit.getButton());
        complaintContainer.getContent().add(complaintManager);
        getContent().add(complaintContainer, BorderLayout.LINE_END);
        repaint();
        validate();
    }


    public MyCustomJButton getSubmit() {
        return submit;
    }

    public JComboBox<BookingReview> getBookingReviewComboBox() {
        return bookingReviewComboBox;
    }

    public JTable getTable() {
        return table;
    }

    public void withListofBookingPanel(JTable t) {
        this.table = t;
        JScrollPane jScrollPane = new JScrollPane(this.table);
        listOfBookingsPanel.add(jScrollPane);
        validate(); repaint();
    }

    public String getTextArea() {
        return textArea.getText();
    }

    public JButton getSendComplaint() {
        return sendComplaint.getButton();
    }
}
