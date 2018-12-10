package views.customComponents;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * This class creates a frame with a framePanel which is where the content will be placed
 * and a main panel that will contains the frame panel and the fotter
 */
public class MyCustomJFrame extends JFrame {

    private final JPanel framePanel;
    private final JPanel mainPanel;

    public MyCustomJFrame(String title, Integer width, Integer height) {
        framePanel = new JPanel();
        JPanel footer = new JPanel();
        this.mainPanel = new JPanel(); mainPanel.setLayout(new BorderLayout());


        /**
         * Every MyCustomJPanel will have this footer, with my student name and ID
         */
        footer.add(new MyCustomJLabel("CREATED BY: Felipe Mantovani - 2017192 Group A", 12).getLabel());
        //mainPanel.add(bar, BorderLayout.NORTH);
        mainPanel.add(framePanel, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel);
        setTitle(title); setSize(width, height); setResizable(false);
        setVisible(true);
    }

    /**
     * killing application when closing the window frame
     */
    public void defaultClose(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * this function validates and repaint frame at one call.
     */
    public void validadeAndRepaint(){
        repaint(); validate();
    }

    /**
     * getter for the frame panel, for the controllers to fill with content
     * @return
     */
    protected JPanel getFramePanel() {
        return framePanel;
    }

    /**
     * adds the menu bar to the frame panel
     * @param menuBar
     */
    public void addMenuBar(JMenuBar menuBar){
        mainPanel.add(menuBar, BorderLayout.NORTH);
    }
}
