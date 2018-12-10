package views;

import views.customComponents.InputPanel;
import java.util.List;

/**
 * This interface is to be implemented by classes that holds many inputs panels such as Forms,
 * Signup registration forms.
 */
public interface InputPanelContainer {

    /**
     * returns an array of all inputspanels that the Frame/Panel contains.
     * @return
     */
    List<InputPanel> getInputsPanel();
}
