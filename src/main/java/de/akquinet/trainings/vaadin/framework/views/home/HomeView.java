package de.akquinet.trainings.vaadin.framework.views.home;

import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;

import java.util.Collection;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface HomeView extends View
{
    void setObserver(Observer observer);

    void setNotes(Collection<Note> notes);

    void showEditor(View editView);

    void closeEditor();

    interface Observer
    {
        void onEdit(long noteId);
    }
}
