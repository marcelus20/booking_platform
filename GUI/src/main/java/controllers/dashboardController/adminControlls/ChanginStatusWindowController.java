package controllers.dashboardController.adminControlls;

import interfaces.Controlls;
import interfaces.Repository;
import interfaces.ViewsObjectGetter;
import models.users.ServiceProvider;
import utils.ArrayListBuilder;
import views.dashboard.adminView.ChangingStatusWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChanginStatusWindowController
        implements Controlls<ChangingStatusWindow>, ViewsObjectGetter<ChangingStatusWindow> {

    private ChangingStatusWindow changingStatusWindow;
    private ServiceProvider selectedServiceProvider;
    private Repository repository;

    public ChanginStatusWindowController(ServiceProvider serviceProvider, Repository repository) {
        this.changingStatusWindow = new ChangingStatusWindow();
        selectedServiceProvider = serviceProvider;
        this.repository = repository;
        config();
        setSizes();
        build();
    }

    @Override
    public void config() {
        changingStatusWindow.getContainer().setLayout(new BorderLayout());
        changingStatusWindow.getServiceProviderModel().setLayout(new GridLayout(0,1));
        changingStatusWindow.setVisible(true);
        changingStatusWindow.repaint();
        changingStatusWindow.validate();
        changingStatusWindow.getApprove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = new StringBuilder()
                        .append("UPDATE service_provider SET approved_status = 'approved' ")
                        .append("WHERE s_id = ")
                        .append(selectedServiceProvider.getId())
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
                        .append(selectedServiceProvider.getId())
                        .append(";").toString();
                repository.executeUpdateQuery(query);
                changingStatusWindow.dispose();
            }
        });
    }

    @Override
    public void build() {
        ArrayListBuilder<JTextField> textFieldList = new ArrayListBuilder()
                .add(new JTextField(selectedServiceProvider.getCompanyFullName()))
                .add(new JTextField(selectedServiceProvider.getLocations().get(0).getFirst_line_address()))
                .add(new JTextField(selectedServiceProvider.getLocations().get(0).getSecond_line_address()))
                .add(new JTextField(selectedServiceProvider.getLocations().get(0).getCity()))
                .add(new JTextField(selectedServiceProvider.getLocations().get(0).getEir_code()));
        textFieldList.build().forEach(jTextField -> {
            jTextField.setEditable(false);
            changingStatusWindow.getServiceProviderModel().add(jTextField);
        });
        changingStatusWindow.getContainer().add(changingStatusWindow.getApprove(), BorderLayout.LINE_START);
        changingStatusWindow.getContainer().add(changingStatusWindow.getDisapprove(), BorderLayout.LINE_END);
        changingStatusWindow.getContainer().add(changingStatusWindow.getServiceProviderModel(),BorderLayout.NORTH);
        changingStatusWindow.add(changingStatusWindow.getContainer());
    }

    @Override
    public void setSizes() {
        changingStatusWindow.setSize(200,200);
    }

    @Override
    public ChangingStatusWindow getViewObject() {
        return changingStatusWindow;
    }
}
