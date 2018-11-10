package views.customComponents;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;


public class MyCustomJFrame extends JFrame {

    private final JPanel framePanel;
    private final JPanel footer;

    public MyCustomJFrame(String title, Integer width, Integer height) {
        framePanel = new JPanel();
        footer = new JPanel();

        JPanel mainPanel = new JPanel(); mainPanel.setLayout(new BorderLayout());

        footer.add(new MyCustomJLabel("CREATED BY: Felipe Mantovani - 2017192 Group A", 12).getLabel());
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
}
