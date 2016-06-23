package de.akquinet.trainings.vaadin.framework.views.noteform;

import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface NoteFormView extends View
{
    void setObserver(final Observer observer);

    void setNote(final Note note);

    Note getNote();

    interface Observer
    {
        void onSave();

        void onCancel();
    }
}
