package de.akquinet.trainings.vaadin.framework.views.overview;

import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;

import java.util.Collection;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface OverviewView extends View
{
    void setObserver(Observer observer);

    void setNotes(Collection<Note> notes);

    void setEditorView(de.akquinet.trainings.vaadin.framework.View view);

    void setEditorVisible(boolean visible);

    interface Observer {
        void onSelect(Note selectedNote);
    }
}
