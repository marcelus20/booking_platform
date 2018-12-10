package views.customComponents;



import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 *This class is generic to TextComponents.
 * Either JPassword or JTextFields can custom be created out of this class
 *
 * Eg of instantiation: MyCustomJField<JTextField> mcTF = new MyCustomJField...
 * @param <T>
 */
public class MyCustomJField<T extends JTextComponent>{ // Meaning, just textFields or passwordFields can be created in this class

    private final T input; // T can either be text field or password field

    /**
     * Constructor 1
     * @param input
     * @param font
     */
    public MyCustomJField(final T input, final MyCustomFont font) {

        this.input = input;
        input.setFont(font.getFont());
    }

    /**
     * Constructor 2
     * @param input
     * @param fontType
     * @param fontSize
     */
    public MyCustomJField(final T input, final String fontType, final Integer fontSize) {
        this.input = input;
        input.setFont(new Font(fontType, Font.PLAIN ,fontSize));
    }

    /**
     * Constructor 3
     * @param input
     * @param fontSize
     */
    public MyCustomJField(final T input, final Integer fontSize){
        this.input = input;
        input.setFont(new MyCustomFont(fontSize).getFont());
    }

    /**
     * Constructor 4
     * @param input
     * @param width
     * @param height
     */
    public MyCustomJField(final T input, final Integer width, final Integer height){
        this.input = input;
        input.setFont(new MyCustomFont(15).getFont());
        this.input.setPreferredSize(new Dimension(width, height));
    }


    /**
     * Constructor 5:
     * @param input
     */
    public MyCustomJField(T input) {
        this.input = input;
        input.setFont(new MyCustomFont().getFont());
    }

    /**
     * getter
     * @return
     */
    public T getInput() {
        return input;
    }
}
