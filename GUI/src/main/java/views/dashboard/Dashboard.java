/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.dashboard;

import views.customComponents.MyCustomJFrame;
import views.customComponents.MyCustomJPanel;
import javax.swing.*;
import java.awt.*;


/**
 * THIS IS THE DASHBOARD CLASS.
 *
 * This dashboard can be built upon admin , service and customers dashboards.
 *
 * This class has the sidebar ad the output panel as main panel
 *
 * The sidebar will be used to hold the buttons options for
 * the user to browse through the dashboard.
 *
 * the output panel is used to store the current panel that user is viewing.
 * @param <T>
 */
public class Dashboard <T extends Dashboard> extends MyCustomJFrame {


    /**
     * List of attributes
     */
    private final JMenuBar menuBar;
    private final JMenuItem menuItem;
    private final JMenu menu;
    private final MyCustomJPanel sideBar;
    private final MyCustomJPanel output;

    /**
     * CONSTRUCTOR
     */
    public Dashboard() {
        super("Dashboard", 1280, 768);
        /**
         * initialising attributes
         */
        menuBar = new JMenuBar();
        menu = new JMenu("Exit");
        menuItem = new JMenuItem("Logout");
        menuBar.add(menu); menu.add(menuItem);
        sideBar = new MyCustomJPanel("Toggle Buttons", 450,600);
        output = new MyCustomJPanel("Console",800,600);
        getFramePanel().setLayout(new BorderLayout());
        getFramePanel().add(menuBar, BorderLayout.NORTH);
        getFramePanel().add(sideBar, BorderLayout.LINE_START); getFramePanel().add(output, BorderLayout.LINE_END);
    }

    /**
     * GETTERS
     * @return
     */
    public JPanel getOutput() {
        return output.getContent();
    }

    public MyCustomJPanel getSideBar() {
        return sideBar;
    }

    public JMenuItem getMenuItem() {
        return menuItem;
    }
}
