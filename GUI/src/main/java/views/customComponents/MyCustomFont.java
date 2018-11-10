package views.customComponents;

import java.awt.Font;

/**
 * This is my custom Font Class. The default constructor is set to be Serif and size 15.
 */

public class MyCustomFont {

    private final Font font;

    public MyCustomFont(final String font) {
        this.font = new Font(font, Font.PLAIN, 15);
    }

    public MyCustomFont() {
        this.font = new Font("Serif", Font.PLAIN, 15);
    }

    public MyCustomFont(final String fontType, final Integer fontSize) {
        this.font = new Font("Serif", Font.PLAIN, fontSize);
    }

    public MyCustomFont(Integer fontSize) {
        this.font = new Font("Serif", Font.PLAIN, fontSize);
    }

    public Font getFont() {
        return font;
    }
}
