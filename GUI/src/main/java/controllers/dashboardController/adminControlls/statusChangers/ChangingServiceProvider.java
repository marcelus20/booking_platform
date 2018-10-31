package controllers.dashboardController.adminControlls.statusChangers;

import interfaces.Repository;
import models.users.ServiceProvider;
import utils.ArrayListBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangingServiceProvider extends ChangingStatusWindowController<ServiceProvider> {

    public ChangingServiceProvider(ServiceProvider userModel, Repository repository) {
        super(userModel, repository);

    }

    @Override
    public void config() {
        super.config();
        changingStatusWindow.setApprove(new JButton("APPROVE"));
        changingStatusWindow.setDisapprove(new JButton("DISAPPROVE"));
        changingStatusWindow.getApprove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = new StringBuilder()
                        .append("UPDATE service_provider SET approved_status = 'approved' ")
                        .append("WHERE s_id = ")
                        .append(model.getId())
                        .append(";").toString();
                repository.executeUpdateQuery(query);
                changingStatusWindow.dispose();
            }
        });
        changingStatusWindow.getDisapprove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = new StringBuilder()
                        .append("UPDATE service_provider SET approved_status = 'disapproved' ")
                        .append("WHERE s_id = ")
                        .append(model.getId())
                        .append(";").toString();
                repository.executeUpdateQuery(query);
                changingStatusWindow.dispose();
            }
        });
    }

    @Override
    public void build() {
        ArrayListBuilder<JTextField> textFieldList = new ArrayListBuilder()
                .add(new JTextField(model.getCompanyFullName()))
                .add(new JTextField(model.getLocations().get(0).getFirst_line_address()))
                .add(new JTextField(model.getLocations().get(0).getSecond_line_address()))
                .add(new JTextField(model.getLocations().get(0).getCity()))
                .add(new JTextField(model.getLocations().get(0).getEir_code()));
        textFieldList.build().forEach(jTextField -> {
            jTextField.setEditable(false);
            changingStatusWindow.getServiceProviderModel().add(jTextField);
        });
        super.build();
    }
}
