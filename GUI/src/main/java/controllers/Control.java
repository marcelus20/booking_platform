package controllers;

/**
 * This interface is to be used with All controllers that manages a GUI view components.
 * The GUI view components usually have fields (textField or PasswordFields) and
 * buttons, that need to be configured and assgined listeners.
 * This interface is to make sure every Controller that controls a view, configure view buttons and
 * inputs with a listener.
 */
public interface Control {

    /**
     * for configuring all buttons of the view component
     */
    void addButtonsAFunction();

    /**
     * for configuring all inputs in the view component
     */
    void addInputsAListener();
}
