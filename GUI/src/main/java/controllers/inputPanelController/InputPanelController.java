package controllers.inputPanelController;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import views.inputPanel.InputPanel;

import javax.swing.*;
import java.awt.*;

public class InputPanelController<T extends InputPanel>
        implements Controlls<T>, ViewsObjectGetter<T> {

    private T inputPanel;

    private InputPanelController(T inputPanel) {
        this.inputPanel = inputPanel;
        config();
        setSizes();
        build();
    }

    public static <T extends InputPanel> InputPanelController<T> initInputPanelController(T inputpanel){
        return new InputPanelController<>(inputpanel);
    }

    @Override
    public void config() {
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
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

    @Override
    public T getViewObject() {
        return inputPanel;
    }

}
