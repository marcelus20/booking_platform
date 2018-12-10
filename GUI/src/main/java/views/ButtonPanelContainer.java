package views;

import views.customComponents.MyCustomJButton;
import java.util.List;

/**
 * This is to be used by the view classes that holds many buttons such as dashboards.
 */
public interface ButtonPanelContainer {

    /**
     * this method will return the list of buttons that a GUI view Frame/panel have
     * TO BE IMPLEMENTED
     * @return the list of Buttons
     */
    public List<MyCustomJButton> getButtonsPanel();
}
