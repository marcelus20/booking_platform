package views.signUpForms;

import controllers.inputPanelController.InputPanelController;
import views.inputPanel.InputPanel;

import javax.swing.*;

public class LocationForm extends JPanel{

    private JLabel formName;
    private InputPanel firstLineAddress;
    private InputPanel secondLineAddress;
    private InputPanel city;
    private InputPanel eirCode;

    public LocationForm() {
        formName = new JLabel("Location form");
        firstLineAddress = InputPanelController.initInputPanelController("First Line Address: ").getViewObject();
        secondLineAddress = InputPanelController.initInputPanelController("Second Line Address: ").getViewObject();
        city = InputPanelController.initInputPanelController("City: ").getViewObject();
        eirCode = InputPanelController.initInputPanelController("Eircode: ").getViewObject();

    }

    public JLabel getFormName() {
        return formName;
    }

    public void setFormName(JLabel formName) {
        this.formName = formName;
    }

    public InputPanel getFirstLineAddress() {
        return firstLineAddress;
    }

    public void setFirstLineAddress(InputPanel firstLineAddress) {
        this.firstLineAddress = firstLineAddress;
    }

    public InputPanel getSecondLineAddress() {
        return secondLineAddress;
    }

    public void setSecondLineAddress(InputPanel secondLineAddress) {
        this.secondLineAddress = secondLineAddress;
    }

    public InputPanel getCity() {
        return city;
    }

    public void setCity(InputPanel city) {
        this.city = city;
    }

    public InputPanel getEirCode() {
        return eirCode;
    }

    public void setEirCode(InputPanel eirCode) {
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
