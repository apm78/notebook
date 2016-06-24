package de.akquinet.trainings.vaadin.framework.views.home;

import com.vaadin.cdi.UIScoped;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class HomeViewImpl implements HomeView
{

    private final VerticalLayout rootLayout = new VerticalLayout();

    private final VerticalLayout noteLayout = new VerticalLayout();

    private Observer observer;

    private Window editorWindow;

    public HomeViewImpl()
    {
        rootLayout.setMargin(true);
        final Label header = new Label("Current Notes");
        header.addStyleName(ValoTheme.LABEL_H2);
        rootLayout.addComponent(header);

        rootLayout.addComponent(noteLayout);
        noteLayout.setSpacing(true);

        rootLayout.addDetachListener(e -> closeEditorWindow());
    }

    @Override
    public void setObserver(final Observer observer)
    {
        this.observer = observer;
    }

    @Override
    public <C> C getComponent(final Class<C> type)
    {
        return type.cast(rootLayout);
    }

    @Override
    public void setNotes(final Collection<Note> notes)
    {
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss",
                UI.getCurrent().getLocale());
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d yyyy",
                UI.getCurrent().getLocale());

        noteLayout.removeAllComponents();
        for (final Note note : notes){
            final Panel panel = new Panel(note.getTitle());
            final VerticalLayout layout = new VerticalLayout();
            layout.setMargin(true);
            layout.setSpacing(true);
            final Label timeLabel = new Label(
                    String.format(UI.getCurrent().getLocale(), "%s on %s",
                            note.getTime().format(timeFormatter),
                            note.getTime().format(dateFormatter)));
            timeLabel.addStyleName(ValoTheme.LABEL_BOLD);
            layout.addComponent(timeLabel);
            layout.addComponent(new Label(note.getDescription()));
            layout.addComponent(new Button("edit", e -> {
                if (observer != null)
                {
                    observer.onEdit(note.getId());
                }
            }));
            panel.setContent(layout);
            noteLayout.addComponent(panel);
        }
    }

    private void closeEditorWindow()
    {
        if (editorWindow != null)
        {
            editorWindow.close();
            editorWindow = null;
        }
    }

    @Override
    public void showEditor(final View editView)
    {
        closeEditorWindow();
        editorWindow = new Window("Edit Note");
        editorWindow.center();
        editorWindow.addCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
        editorWindow.setModal(true);
        editorWindow.setClosable(true);
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.addComponent(editView.getComponent(Component.class));
        editorWindow.setContent(verticalLayout);
        editorWindow.addCloseListener(e -> editorWindow = null);
        rootLayout.getUI().addWindow(editorWindow);
    }

    @Override
    public void closeEditor()
    {
        closeEditorWindow();
    }
}
