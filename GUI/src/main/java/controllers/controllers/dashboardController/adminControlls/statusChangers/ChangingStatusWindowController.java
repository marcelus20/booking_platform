package controllers.controllers.dashboardController.adminControlls.statusChangers;

import controllers.controllers.Controlls;
import controllers.repositories.Repository;
import controllers.controllers.ViewsObjectGetter;
import views.dashboard.adminView.ChangingStatusWindow;
import java.awt.*;


public class ChangingStatusWindowController<T>
        implements Controlls<ChangingStatusWindow>, ViewsObjectGetter<ChangingStatusWindow> {

    protected ChangingStatusWindow changingStatusWindow;
    protected T model;
    protected Repository repository;

    public ChangingStatusWindowController(T model, Repository repository) {
        this.changingStatusWindow = new ChangingStatusWindow();
        this.model = model;
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
    }

    @Override
    public void build() {
        changingStatusWindow.getContainer().add(changingStatusWindow.getApprove(), BorderLayout.LINE_START);
        changingStatusWindow.getContainer().add(changingStatusWindow.getDisapprove(), BorderLayout.LINE_END);
        changingStatusWindow.getContainer().add(changingStatusWindow.getServiceProviderModel(),BorderLayout.NORTH);
        changingStatusWindow.add(changingStatusWindow.getContainer());
    }

    @Override
    public void setSizes() {
        changingStatusWindow.setSize(300,250);
    }

    @Override
    public ChangingStatusWindow getViewObject() {
        return changingStatusWindow;
    }
}
