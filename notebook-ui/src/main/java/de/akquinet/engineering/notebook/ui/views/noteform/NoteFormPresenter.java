package de.akquinet.engineering.notebook.ui.views.noteform;

import de.akquinet.engineering.notebook.ui.Presenter;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface NoteFormPresenter extends Presenter
{
    void setNote(NoteDto note);

    void setObserver(Observer observer);
    
    interface Observer
    {
        void onSave();

        void onCancel();
    }
}
