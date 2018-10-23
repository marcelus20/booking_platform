package controllers.inputPanelController;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import views.inputPanel.InputPanel;
import views.inputPanel.InputPasswordPanel;

import javax.swing.*;
import java.awt.*;

public class InputPasswordController implements Controlls, ViewsObjectGetter {
    private InputPasswordPanel inputPasswordPanel;

    public InputPasswordController(String label, InputPasswordPanel inputPanel) {

        inputPasswordPanel = inputPanel;
    }

    public InputPasswordController(final String label) {
        inputPasswordPanel = new InputPasswordPanel(label);
        config();
        setSizes();
        build();
    }

    public static InputPasswordController initInputPasswordController(final String label){
        return new InputPasswordController(label);
    }



    @Override
    public void config() {
        inputPasswordPanel.setLayout(new BoxLayout(inputPasswordPanel, BoxLayout.X_AXIS));
        //inputPanel.getInputLabel().setAlignmentX(Component.CENTER_ALIGNMENT);
        //inputPanel.getPassword().setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void build() {
        inputPasswordPanel.add(inputPasswordPanel.getInputLabel());
        inputPasswordPanel.add(inputPasswordPanel.getPassword());
    }

    @Override
    public void setSizes() {


        inputPasswordPanel.getPassword().setPreferredSize(new Dimension(200,10));
        inputPasswordPanel.getInputLabel().setPreferredSize(new Dimension(200,20));
        inputPasswordPanel.getPassword().setFont(new Font("Courier", Font.BOLD,15));
        inputPasswordPanel.getInputLabel().setFont(new Font("Courier", Font.BOLD,12));


    }

    public InputPasswordPanel getInputPanel() {
        return inputPasswordPanel;
    }

    public void setInputPanel(InputPasswordPanel inputPasswordPanel) {
        this.inputPasswordPanel = inputPasswordPanel;
    }

    @Override
    public InputPasswordPanel getViewObject() {
        return getInputPanel();
    }
}
