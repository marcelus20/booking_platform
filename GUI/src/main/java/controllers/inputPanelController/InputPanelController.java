package controllers.inputPanelController;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import views.inputPanel.InputPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InputPanelController implements Controlls, ViewsObjectGetter<InputPanel> {



    private InputPanel inputPanel;

    public InputPanelController(InputPanel inputPanel) {

        this.inputPanel = inputPanel;
    }

    public InputPanelController(final String label) {
        inputPanel = new InputPanel(label);
        config();
        setSizes();
        build();
    }

    public static InputPanelController initInputPanelController(final String label){
        return new InputPanelController(label);
    }




    @Override
    public void config() {
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        //inputPanel.getInputLabel().setAlignmentX(Component.CENTER_ALIGNMENT);
        //inputPanel.getPassword().setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void build() {
        inputPanel.add(inputPanel.getInputLabel());
        inputPanel.add(inputPanel.getInput());
    }

    @Override
    public void setSizes() {


        inputPanel.getInput().setPreferredSize(new Dimension(200,10));
        inputPanel.getInputLabel().setPreferredSize(new Dimension(200,20));
        inputPanel.getInput().setFont(new Font("Courier", Font.BOLD,15));
        inputPanel.getInputLabel().setFont(new Font("Courier", Font.BOLD,15));


    }

    public InputPanel getInputPanel() {
        return inputPanel;
    }

    public void setInputPanel(InputPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    @Override
    public InputPanel getViewObject() {
        return getInputPanel();
    }
}
