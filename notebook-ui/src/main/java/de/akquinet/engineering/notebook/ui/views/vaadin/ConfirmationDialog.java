package de.akquinet.engineering.notebook.ui.views.vaadin;


import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class ConfirmationDialog
{
    private final Window window = new Window();
    private final Button okButton = new Button();
    private final Button cancelButton = new Button();
    private final Label description = new Label();

    public ConfirmationDialog()
    {
        window.setModal(true);
        window.setResizable(false);
        window.setDraggable(false);
        window.setClosable(true);
        window.center();
        window.addCloseShortcut(ShortcutAction.KeyCode.ESCAPE);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("100%");
        buttonLayout.addComponents(cancelButton, okButton);
        okButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.BOTTOM_RIGHT);
        buttonLayout.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
        buttonLayout.setExpandRatio(cancelButton, 1f);
        buttonLayout.setSpacing(true);

        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        verticalLayout.addComponents(description, buttonLayout);
        window.setContent(verticalLayout);

        // defaults
        cancelButton.addClickListener(e -> window.close());
        cancelButton.setCaption("Cancel");
        okButton.setCaption("Confirm");
        okButton.addClickListener(e -> window.close());
    }

    public Window getWindow()
    {
        return window;
    }

    public Button getOkButton()
    {
        return okButton;
    }

    public Button getCancelButton()
    {
        return cancelButton;
    }

    public Label getDescription()
    {
        return description;
    }

    public void show(){
        UI.getCurrent().addWindow(window);
    }

    public void close(){
        window.close();
    }
}
