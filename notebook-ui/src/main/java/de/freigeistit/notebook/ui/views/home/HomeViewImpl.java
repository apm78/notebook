package de.freigeistit.notebook.ui.views.home;

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
import de.freigeistit.notebook.ui.View;
import de.freigeistit.notebook.ui.i18n.I18n;
import de.freigeistit.notebook.ui.model.NoteDto;
import de.freigeistit.notebook.ui.model.NoteModel;
import de.freigeistit.notebook.ui.views.noteform.NoteForm;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

/**
 * @author Axel P. Meier
 */
@UIScoped
public class HomeViewImpl implements HomeView
{
    @Inject
    private I18n i18n;

    @Inject
    private NoteModel noteModel;

    private final VerticalLayout rootLayout = new VerticalLayout();

    private final VerticalLayout noteLayout = new VerticalLayout();

    private Window editorWindow;

    private NoteForm noteForm;

    public HomeViewImpl()
    {
    }

    @PostConstruct
    public void init(){
        noteForm = new NoteForm(i18n);

        rootLayout.setMargin(true);
        final Label header = new Label(i18n.get("home.title"));
        header.addStyleName(ValoTheme.LABEL_H2);
        rootLayout.addComponent(header);

        rootLayout.addComponent(noteLayout);
        noteLayout.setSpacing(true);

        rootLayout.addDetachListener(e -> closeEditorWindow());
    }

    @Override
    public void onEnter() {
        setNotes(getNotesSortedByDateAscNotOlder1Hour());
    }

    @Override
    public <C> C getComponent(final Class<C> type)
    {
        return type.cast(rootLayout);
    }

    private void setNotes(final Collection<NoteDto> notes)
    {
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(i18n.get("home.timePattern"),
                UI.getCurrent().getLocale());
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(i18n.get("home.datePattern"),
                UI.getCurrent().getLocale());

        noteLayout.removeAllComponents();
        for (final NoteDto note : notes){
            final Panel panel = new Panel(note.getTitle());
            final VerticalLayout layout = new VerticalLayout();
            layout.setMargin(true);
            layout.setSpacing(true);
            final Label timeLabel = new Label(
                    i18n.get("home.timeOnDate",
                            note.getTime().format(timeFormatter),
                            note.getTime().format(dateFormatter)));
            timeLabel.addStyleName(ValoTheme.LABEL_BOLD);
            layout.addComponent(timeLabel);
            layout.addComponent(new Label(note.getDescription()));
            layout.addComponent(new Button(i18n.get("home.editButton.caption"), e -> onEdit(note)));
            panel.setContent(layout);
            noteLayout.addComponent(panel);
        }
    }

    private void onEdit(final NoteDto note){
        showEditor(note);
    }

    private void closeEditorWindow()
    {
        if (editorWindow != null)
        {
            editorWindow.close();
            editorWindow = null;
        }
    }

    private void save(){
        final NoteDto noteToSave = noteForm.getNote();
        noteModel.updateNote(noteToSave);

        closeEditorWindow();
        setNotes(getNotesSortedByDateAscNotOlder1Hour());
    }

    private void cancel(){
        closeEditorWindow();
    }

    private void showEditor(final NoteDto note)
    {
        noteForm.setObserver(new NoteForm.Observer() {
            @Override
            public void onSave() {
                save();
            }

            @Override
            public void onCancel() {
                cancel();
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

    private List<NoteDto> getNotesSortedByDateAscNotOlder1Hour()
    {
        return noteModel.getNotesSortedByDateAscNotOlderThan(LocalDateTime.now().minusHours(1));
    }
}
