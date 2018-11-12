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
public class MyCustomJField<T extends JTextComponent>{

    private final T input;

    public MyCustomJField(final T input, final MyCustomFont font) {

        this.input = input;
        input.setFont(font.getFont());
    }

    public MyCustomJField(final T input, final String fontType, final Integer fontSize) {
        this.input = input;
        input.setFont(new Font(fontType, Font.PLAIN ,fontSize));
    }

    public MyCustomJField(final T input, final Integer fontSize){
        this.input = input;
        input.setFont(new MyCustomFont(fontSize).getFont());
    }

    public MyCustomJField(final T input, final Integer width, final Integer height){
        this.input = input;
        input.setFont(new MyCustomFont(15).getFont());
        this.input.setPreferredSize(new Dimension(width, height));
    }


    public MyCustomJField(T input) {
        this.input = input;
        input.setFont(new MyCustomFont().getFont());
    }

    public T getInput() {
        return input;
    }
}
