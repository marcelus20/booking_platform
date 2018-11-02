package views.signUpForms;

import controllers.controllers.inputPanelController.InputPanelController;
import views.inputPanel.TextFieldPanel;

import javax.swing.*;

public class LocationForm extends JPanel{

    private JLabel formName;
    private TextFieldPanel firstLineAddress;
    private TextFieldPanel secondLineAddress;
    private TextFieldPanel city;
    private TextFieldPanel eirCode;

    public LocationForm() {
        formName = new JLabel("Location form");
        firstLineAddress = InputPanelController.initInputPanelController(new TextFieldPanel("First Line Address: ")).getViewObject();
        secondLineAddress = InputPanelController.initInputPanelController(new TextFieldPanel("Second Line Address: ")).getViewObject();
        city = InputPanelController.initInputPanelController(new TextFieldPanel("City: ")).getViewObject();
        eirCode = InputPanelController.initInputPanelController(new TextFieldPanel("Eircode: ")).getViewObject();

    }

    public JLabel getFormName() {
        return formName;
    }

    public void setFormName(JLabel formName) {
        this.formName = formName;
    }

    public TextFieldPanel getFirstLineAddress() {
        return firstLineAddress;
    }

    public void setFirstLineAddress(TextFieldPanel firstLineAddress) {
        this.firstLineAddress = firstLineAddress;
    }

    public TextFieldPanel getSecondLineAddress() {
        return secondLineAddress;
    }

    public void setSecondLineAddress(TextFieldPanel secondLineAddress) {
        this.secondLineAddress = secondLineAddress;
    }

    public TextFieldPanel getCity() {
        return city;
    }

    public void setCity(TextFieldPanel city) {
        this.city = city;
    }

    public TextFieldPanel getEirCode() {
        return eirCode;
    }

    public void setEirCode(TextFieldPanel eirCode) {
        this.eirCode = eirCode;
    }

    /*
    private JLabel formName;


    private InputPanel firstLineAddress;
    private InputPanel secondLineAddress;
    private InputPanel city;
    private InputPanel eirCode;

    public LocationForm(JLabel formName, InputPanel firstLineAddress, InputPanel secondLineAddress, InputPanel city, InputPanel eirCode) {
        this.formName = formName;
        this.firstLineAddress = firstLineAddress;
        this.secondLineAddress = secondLineAddress;
        this.city = city;
        this.eirCode = eirCode;
    }

    public LocationForm() {
        formName = new JLabel("Sign up as a Service Provider!");
        firstLineAddress = InputPanelController.initInputPanelController("First Line address: ").getViewObject();
        secondLineAddress = InputPanelController.initInputPanelController("Second Line Address: ").getViewObject();
        city = InputPanelController.initInputPanelController("City: ").getViewObject();
        eirCode = InputPanelController.initInputPanelController("EirCode").getViewObject();
    }

    public JLabel getFormName() {
        return formName;
    }

    public void setFormName(JLabel formName) {
        this.formName = formName;
    }

    public JTextField getFirstLineAddress() {
        return firstLineAddress.getPassword();
    }

    public void setFirstLineAddress(InputPanel firstLineAddress) {
        this.firstLineAddress = firstLineAddress;
    }

    public JTextField getSecondLineAddress() {
        return secondLineAddress.getPassword();
    }

    public void setSecondLineAddress(InputPanel secondLineAddress) {
        this.secondLineAddress = secondLineAddress;
    }

    public JTextField getCity() {
        return city.getPassword();
    }

    public void setCity(InputPanel city) {
        this.city = city;
    }

    public JTextField getEirCode() {
        return eirCode.getPassword();
    }

    public void setEirCode(InputPanel eirCode) {
        this.eirCode = eirCode;
    }
    */
}
