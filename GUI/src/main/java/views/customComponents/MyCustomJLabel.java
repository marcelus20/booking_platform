/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.customComponents;


import models.utils.Tools;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a custom Label.
 * The reason of the creation of this class is to make easier the criation of JLabels with the desired
 * Font and size.
 */

public class MyCustomJLabel{

    /**
     * Attribute
     */
    private final JLabel label;

    /**
     * CONSTRUCTOR 1
     * @param label
     * @param font
     */
    public MyCustomJLabel(final String label, final Font font) {
        this.label = new JLabel(Tools.wrapStringWithHtml(label), SwingConstants.CENTER);
        this.label.setFont(font);
    }

    /**
     * CONSTRUCTOR 2
     * @param label
     * @param fontType
     * @param fontSize
     */
    public MyCustomJLabel(final String label, final String fontType, final Integer fontSize) {
        this.label = new JLabel(Tools.wrapStringWithHtml(label), SwingConstants.CENTER);
        this.label.setFont(new Font(fontType, Font.PLAIN, fontSize));
    }

    /**
     * CONSTRUCTOR 3
     * @param label
     */
    public MyCustomJLabel(final String label) {
        this.label = new JLabel(Tools.wrapStringWithHtml(label), SwingConstants.CENTER);
        this.label.setFont(new MyCustomFont().getFont());
    }

    /**
     * CONSTRUCTOR 4
     * @param label
     * @param fontSize
     */
    public MyCustomJLabel(final String label, final Integer fontSize) {
        this.label = new JLabel(Tools.wrapStringWithHtml(label), SwingConstants.CENTER);
        this.label.setFont(new MyCustomFont(fontSize).getFont());
    }

    /**
     * GETTER FOR THE LABEL
     * @return
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Sets font to white color
     */
    public void setFontToWhite(){
        this.label.setForeground(Color.WHITE);
    }


}
