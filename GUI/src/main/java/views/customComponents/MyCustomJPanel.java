/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.customComponents;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This is my custom JPanel class. very well used throughout the the project.
 * The special thing about this class is that it is a JPanel that comes with a title,
 * border, and a panel with the content to be filled by the controllers.
 *
 * You just need to use one of the constructors and then everything else will be configured
 * in a nice layout, title and content.
 */
public class MyCustomJPanel extends JPanel{

    /**
     * List of attributes
     */
    private final MyCustomJLabel title;
    private final Border border;
    private final Dimension size;
    private final JPanel header;
    private JPanel content; // -> this guy is the only one that controllers will fill with content

    /**
     * Constructor
     * @param title
     * @param width
     * @param height
     */
    public MyCustomJPanel(String title, Integer width, Integer height) {
        //CONFIGURING ALL GUI COMPONENTS
        this.title = new MyCustomJLabel(title); this.title.setFontToWhite();
        this.border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        this.size = new Dimension(width, height);
        header = new JPanel();
        content = new JPanel();
        header.add(this.title.getLabel());
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH); add(new JScrollPane(content), BorderLayout.CENTER);
        header.setBackground(Color.DARK_GRAY);
        content.setBackground(Color.LIGHT_GRAY);
        setPreferredSize(size); setBorder(border);
    }

    /**
     * GETTERS AND SETTERS
     * @return
     */
    public JPanel getContent() {
        return content;
    }

    public MyCustomJPanel withContent(JPanel content){
        this.content = content;
        return this;
    }

    public void setTitle(String title){
        this.title.getLabel().setText(title);
    }
}
