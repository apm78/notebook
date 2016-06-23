package de.akquinet.trainings.vaadin.framework.views.home;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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

    public HomeViewImpl()
    {
        rootLayout.setMargin(true);
        rootLayout.addComponent(noteLayout);

        noteLayout.setCaption("Current Notes");
        noteLayout.setSpacing(true);
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
            layout.addComponent(new Label(
                    String.format(UI.getCurrent().getLocale(), "%s on %s",
                            note.getTime().format(timeFormatter),
                            note.getTime().format(dateFormatter))));
            layout.addComponent(new Label(note.getDescription()));
            panel.setContent(layout);
            noteLayout.addComponent(panel);
        }
    }
}
