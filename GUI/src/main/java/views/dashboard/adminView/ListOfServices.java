package views.dashboard.adminView;

import static controllers.controllers.inputPanelController.ComboBoxController.initComboBoxController;

import views.inputPanel.ComboBox;


import javax.swing.*;

public class ListOfServices extends TableOfEntities {

    private JPanel filterPanel;
    private ComboBox locationFilter;
    private ComboBox nameOfCompanyFilter;

    public ListOfServices() {
        title = new JLabel("List of Services Providers");
        filterPanel = new JPanel();


        nameOfCompanyFilter = initComboBoxController("Search by Company Name").getViewObject();
        locationFilter = initComboBoxController("Search by city").getViewObject();
    }

    public JPanel getFilterPanel() {
        return filterPanel;
    }

    public ComboBox getLocationFilter() {
        return locationFilter;
    }

    public ComboBox getNameOfCompanyFilter() {
        return nameOfCompanyFilter;
    }

    public void setFilterPanel(JPanel filterPanel) {
        this.filterPanel = filterPanel;
    }

    public void setLocationFilter(ComboBox locationFilter) {
        this.locationFilter = locationFilter;
    }

    public void setNameOfCompanyFilter(ComboBox nameOfCompanyFilter) {
        this.nameOfCompanyFilter = nameOfCompanyFilter;
    }
}
