package views.customComponents;


import javax.swing.*;
import java.awt.*;

/**
 * This class is a custom Label.
 * The reason of the creation of this class is to make easier the criation of JLabels with the desired
 * Font and size.
 */

public class MyCustomJLabel{

    private final JLabel label;

    public MyCustomJLabel(final String label, final Font font) {

        this.label = new JLabel(label, SwingConstants.CENTER);
        this.label.setFont(font);
    }

    public MyCustomJLabel(final String label, final String fontType, final Integer fontSize) {
        this.label = new JLabel(label, SwingConstants.CENTER);
        this.label.setFont(new Font(fontType, Font.PLAIN, fontSize));
    }

    public MyCustomJLabel(final String label) {
        this.label = new JLabel(label, SwingConstants.CENTER);
        this.label.setFont(new MyCustomFont().getFont());
    }

    public MyCustomJLabel(final String label, final Integer fontSize) {
        this.label = new JLabel(label, SwingConstants.CENTER);
        this.label.setFont(new MyCustomFont(fontSize).getFont());
    }

    public JLabel getLabel() {
        return label;
    }

    public void setFontToWhite(){
        this.label.setForeground(Color.WHITE);
    }


}
