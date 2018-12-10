package views.customComponents;

import java.awt.Font;

/**
 * This is my custom Font Class. The default constructor is set to be Serif and size 15.
 */

public class MyCustomFont {

    /**
     * attribute
     */
    private final Font font;

    /**
     * First constructor: just takes Font
     * @param font
     */
    public MyCustomFont(final String font) {
        this.font = new Font(font, Font.PLAIN, 15);
    }

    /**
     * Second constructor: empty constructor
     */
    public MyCustomFont() {
        this.font = new Font("Serif", Font.PLAIN, 15);
    }

    /**
     * Third constructor: takes font type and size.
     * @param fontType
     * @param fontSize
     */
    public MyCustomFont(final String fontType, final Integer fontSize) {
        this.font = new Font(fontType, Font.PLAIN, fontSize);
    }

    /**
     * Fourth takes just fontSize and sets to serif the name
     * @param fontSize
     */
    public MyCustomFont(Integer fontSize) {
        this.font = new Font("Serif", Font.PLAIN, fontSize);
    }

    //getter
    public Font getFont() {
        return font;
    }
}
