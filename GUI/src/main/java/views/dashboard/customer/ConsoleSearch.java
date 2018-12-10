/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.dashboard.customer;

import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a panel that will show the elements for the user to search for Services Providers
 */
public class ConsoleSearch extends MyCustomJPanel {

    /**
     * List of attributes, a button to toggle the combobox with the list of cities
     */
    private MyCustomJButton searchByCity;
    /**
     * A container to be filled with the resulting list
     */
    private MyCustomJPanel container;

    /**
     * Constructor
     */
    public ConsoleSearch() {
        super("Search Barber or Hairdresser", 700, 500);
        searchByCity = new MyCustomJButton("Search By City");
        container = new MyCustomJPanel("Results - click on one of the table row", 650, 450);
        getContent().setLayout(new BorderLayout());
        getContent().add(searchByCity.getButton(), BorderLayout.NORTH);getContent().add(container, BorderLayout.CENTER);
    }

    /**
     * getters
     * @return
     */
    public JButton getSearchByCity() {
        return searchByCity.getButton();
    }

    public JPanel getContainer() {
        return container.getContent();
    }
}
