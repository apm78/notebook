package de.akquinet.trainings.vaadin.framework.views.noteform;

import de.akquinet.trainings.vaadin.framework.Presenter;
import de.akquinet.trainings.vaadin.framework.backend.Note;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface NoteFormPresenter extends Presenter
{
    void setNote(Note note);

    void setObserver(Observer observer);
    
    interface Observer
    {
        void onSave();

        void onCancel();
    }
}
