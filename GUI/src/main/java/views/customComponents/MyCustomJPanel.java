package views.customComponents;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyCustomJPanel extends JPanel{

    private final MyCustomJLabel title;
    private final Border border;
    private final Dimension size;
    private final JPanel header;
    private final JPanel content;

    public MyCustomJPanel(String title, Integer width, Integer height) {
        this.title = new MyCustomJLabel(title); this.title.setFontToWhite();
        this.border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        this.size = new Dimension(width, height);
        header = new JPanel();
        content = new JPanel();
        header.add(this.title.getLabel());
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH); add(content, BorderLayout.CENTER);
        header.setBackground(Color.DARK_GRAY);
        content.setBackground(Color.LIGHT_GRAY);
        setPreferredSize(size); setBorder(border);
    }

    public JPanel getContent() {
        return content;
    }

    public void setTitle(String title){
        this.title.getLabel().setText(title);
    }
}
