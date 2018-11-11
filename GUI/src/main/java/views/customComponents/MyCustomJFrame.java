package views.customComponents;

import javax.swing.*;
import java.awt.BorderLayout;


public class MyCustomJFrame extends JFrame {

    private final JPanel framePanel;
    private final JPanel mainPanel;

    public MyCustomJFrame(String title, Integer width, Integer height) {
        framePanel = new JPanel();
        JPanel footer = new JPanel();
        this.mainPanel = new JPanel(); mainPanel.setLayout(new BorderLayout());


        footer.add(new MyCustomJLabel("CREATED BY: Felipe Mantovani - 2017192 Group A", 12).getLabel());
        //mainPanel.add(bar, BorderLayout.NORTH);
        mainPanel.add(framePanel, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
        setTitle(title); setSize(width, height); setResizable(false);
        setVisible(true);
    }

    public void defaultClose(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void validadeAndRepaint(){
        repaint(); validate();
    }

    protected JPanel getFramePanel() {
        return framePanel;
    }

    public void addMenuBar(JMenuBar menuBar){
        mainPanel.add(menuBar, BorderLayout.NORTH);
    }
}
