package de.freigeistit.notebook.ui.views.noteform;

import de.freigeistit.notebook.ui.Presenter;
import de.freigeistit.notebook.ui.model.NoteDto;

/**
 * @author Axel P. Meier
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
