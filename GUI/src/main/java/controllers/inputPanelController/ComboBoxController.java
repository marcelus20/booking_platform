package controllers.inputPanelController;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import views.inputPanel.ComboBox;

import javax.swing.*;
import java.awt.*;

public class ComboBoxController implements Controlls<ComboBox>, ViewsObjectGetter<ComboBox> {
    private ComboBox comboBox;


    private ComboBoxController(String label) {
        comboBox = new ComboBox(label);
        config();
        setSizes();
        build();
    }

    public static ComboBoxController initComboBoxController(String label){
        return new ComboBoxController(label);
    }

    @Override
    public void config() {
        comboBox.setLayout(new BorderLayout());
    }

    @Override
    public void build() {
        comboBox.add(comboBox.getInputLabel(), BorderLayout.LINE_START);
        comboBox.add(comboBox.getInput(), BorderLayout.LINE_END);
    }

    @Override
    public void setSizes() {

    }

    @Override
    public ComboBox getViewObject() {
        return comboBox;
    }
}
