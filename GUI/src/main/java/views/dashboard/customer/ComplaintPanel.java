/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.dashboard.customer;

import models.enums.BookingReview;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Complaint view Panel, for Customers to add a review about the a booking (bookings will be listed
 * just if it is set to COMPLETE by a service provider) and add a complaint about a service provider.
 *
 */
public class ComplaintPanel extends MyCustomJPanel {

    /**
     * List of attributes.
     */
    private JTable table;//to list the Bookings with the complete status.
    private final MyCustomJPanel listOfBookingsPanel; // the panel for displaying the table
    private final MyCustomJPanel complaintContainer;// the panel for displaying the complaint area
    private final MyCustomJButton submit; //the button to save the review status to DB
    private final JComboBox<BookingReview> bookingReviewComboBox; // the combo box with the review options (See Review enum)
    private final MyCustomJPanel complaintManager;// area for writing down a complaint
    private final JTextArea textArea; // input that will store the text about the complaint
    private final MyCustomJButton sendComplaint; // button to save the complaint about a service provider in the database

    /**
     * Constructor:
     * @param options
     */
    public ComplaintPanel(BookingReview[] options) {
        super("Review/Complaints panel - Only applied for bookings with COMPLETE status", 750, 700);
        submit = new MyCustomJButton("Submit", 100,40);
        bookingReviewComboBox = new JComboBox<BookingReview>(options);
//        bookingReviewComboBox.setPreferredSize(new Dimension(300,300));
        listOfBookingsPanel = new MyCustomJPanel("Click on a row to see details", 300, 10);
        complaintContainer = new MyCustomJPanel("Review Manager", 300, 10);
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

    /**
     * this method adds the complaintContainer in the "content" panel of the super class.
     */
    public void showComplaintContainer(){
        complaintContainer.getContent().setLayout(new GridLayout(5,1));
        complaintContainer.getContent().add(bookingReviewComboBox);
        complaintContainer.getContent().add(submit.getButton());
        complaintContainer.getContent().add(complaintManager);
        getContent().add(complaintContainer, BorderLayout.LINE_END);
        repaint();
        validate();
    }


    /**
     * GETTERS
     * @return
     */
    public MyCustomJButton getSubmit() {
        return submit;
    }

    public JComboBox<BookingReview> getBookingReviewComboBox() {
        return bookingReviewComboBox;
    }

    public JTable getTable() {
        return table;
    }

    /**
     * SETTERS
     * @param t
     */

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
