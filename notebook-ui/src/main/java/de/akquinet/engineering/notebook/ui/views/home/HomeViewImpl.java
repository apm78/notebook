package de.akquinet.engineering.notebook.ui.views.home;

import com.vaadin.cdi.UIScoped;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;
import de.akquinet.engineering.notebook.ui.views.noteform.NoteForm;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;

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

    private final NoteForm noteForm = new NoteForm();

    public HomeViewImpl()
    {
    }

    @PostConstruct
    public void init()
    {
        rootLayout.setMargin(true);
        final Label header = new Label("Home");
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
    public void setNotes(final Collection<NoteDto> notes)
    {
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm",
                UI.getCurrent().getLocale());
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d yyyy",
                UI.getCurrent().getLocale());

        noteLayout.removeAllComponents();
        for (final NoteDto note : notes)
        {
            final Panel panel = new Panel(note.getTitle());
            final VerticalLayout layout = new VerticalLayout();
            layout.setMargin(true);
            layout.setSpacing(true);
            final Label timeLabel = new Label(
                    new MessageFormat("{0} on {1}", Locale.US)
                            .format(new Object[]{
                                    note.getTime().format(timeFormatter),
                                    note.getTime().format(dateFormatter)}));
            timeLabel.addStyleName(ValoTheme.LABEL_BOLD);
            layout.addComponent(timeLabel);
            layout.addComponent(new Label(note.getDescription()));
            layout.addComponent(new Button("Edit", e ->
            {
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
    public void showEditor(final NoteDto note)
    {
        noteForm.setObserver(new NoteForm.Observer()
        {
            @Override
            public void onSave()
            {
                if (observer != null)
                {
                    observer.onSave();
                }
            }

            @Override
            public void onCancel()
            {
                if (observer != null)
                {
                    observer.onCancel();
                }
            }
        });
        noteForm.setNote(note);

        closeEditorWindow();
        editorWindow = new Window();
        editorWindow.center();
        editorWindow.addCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
        editorWindow.setModal(true);
        editorWindow.setClosable(true);
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.addComponent(noteForm.getRootLayout());
        editorWindow.setContent(verticalLayout);
        editorWindow.addCloseListener(e -> editorWindow = null);
        rootLayout.getUI().addWindow(editorWindow);
    }

    @Override
    public NoteDto getNote()
    {
        return noteForm.getNote();
    }

    @Override
    public void closeEditor()
    {
        closeEditorWindow();
    }
}
