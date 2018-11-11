package views;

import views.customComponents.MyCustomJFrame;
import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends MyCustomJFrame {

    private final JMenuBar menuBar;
    private final JMenuItem menuItem;
    private final JMenu menu;
    private final MyCustomJPanel sideBar;
    private final MyCustomJPanel output;



    public Dashboard() {
        super("Dashboard", 1280, 768);
        menuBar = new JMenuBar();
        menu = new JMenu("Exit");
        menuItem = new JMenuItem("Logout");

        menuBar.add(menu); menu.add(menuItem);


        sideBar = new MyCustomJPanel("Toggle Buttons", 450,600);
        output = new MyCustomJPanel("Console",800,600);

        sideBar.getContent().add(new MyCustomJLabel("side Bar", 50).getLabel());
        output.getContent().add(new MyCustomJLabel("output", 50).getLabel());
        getFramePanel().setLayout(new BorderLayout());
        getFramePanel().add(menuBar, BorderLayout.NORTH);
        getFramePanel().add(sideBar, BorderLayout.LINE_START); getFramePanel().add(output, BorderLayout.LINE_END);
    }
}
