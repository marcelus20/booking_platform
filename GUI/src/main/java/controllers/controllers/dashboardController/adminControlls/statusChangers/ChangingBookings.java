package controllers.controllers.dashboardController.adminControlls.statusChangers;

import controllers.repositories.Repository;
import models.Bookings;
import controllers.repositories.BookingsRepository;
import utils.ArrayListBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangingBookings extends ChangingStatusWindowController<Bookings> {
    public ChangingBookings(Bookings userModel, Repository repository) {
        super(userModel, repository);
    }

    @Override
    public void config() {
        super.config();
        changingStatusWindow.setApprove(new JButton("Cancel Appointment"));
        changingStatusWindow.setDisapprove(new JButton("Go Back"));
        changingStatusWindow.getApprove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(repository instanceof BookingsRepository);
                String query = new StringBuilder()
                        .append("DELETE FROM booking ")
                        .append("WHERE s_id = ")
                        .append(model.getServiceId())
                        .append(" AND customer_id = ")
                        .append(model.getCustomerId())
                        .append(";").toString();
                System.out.println(query);
                repository.executeUpdateQuery(query);
                changingStatusWindow.dispose();
            }});
        changingStatusWindow.getDisapprove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changingStatusWindow.dispose();
            }
        });
    }

    @Override
    public void build() {
        ArrayListBuilder<JTextField> textFieldList = new ArrayListBuilder()
                .add(new JTextField(String.valueOf("Customer_ID: "+model.getServiceId())))
                .add(new JTextField(String.valueOf("Service_ID" +model.getCustomerId())))
                .add(new JTextField(String.valueOf("TIME: "+model.getTime())));

        textFieldList.build().forEach(jTextField -> {
            jTextField.setEditable(false);
            changingStatusWindow.getServiceProviderModel().add(jTextField);
        });
        super.build();
    }
}
