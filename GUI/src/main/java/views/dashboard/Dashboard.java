package views.dashboard;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    private JMenuBar menu;
    private JPanel sideBar;
    private JPanel output;
    private JPanel footer;

    public Dashboard() throws HeadlessException {
        menu = new JMenuBar();
        sideBar = new JPanel();
        output = new JPanel();
        footer = new JPanel();
    }

    public JMenuBar getMenu() {
        return menu;
    }

    public void setMenu(JMenuBar menu) {
        this.menu = menu;
    }

    public JPanel getSideBar() {
        return sideBar;
    }

    public void setSideBar(JPanel sideBar) {
        this.sideBar = sideBar;
    }

    public JPanel getOutput() {
        return output;
    }

    public void setOutput(JPanel output) {
        this.output = output;
    }

    public JPanel getFooter() {
        return footer;
    }

    public void setFooter(JPanel footer) {
        this.footer = footer;
    }
}
