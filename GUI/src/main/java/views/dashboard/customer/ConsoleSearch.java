package views.dashboard.customer;

import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;

public class ConsoleSearch extends MyCustomJPanel {

    private MyCustomJButton searchByCity;
    private MyCustomJPanel container;

    public ConsoleSearch() {
        super("Search Barber or Hairdresser", 700, 500);

        searchByCity = new MyCustomJButton("Search By City");
        container = new MyCustomJPanel("Results", 650, 450);
        getContent().add(searchByCity.getButton());getContent().add(container);
    }

    public JButton getSearchByCity() {
        return searchByCity.getButton();
    }

    public JPanel getContainer() {
        return container.getContent();
    }
}
