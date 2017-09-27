package de.freigeistit.notebook.ui.views.noteform;

import de.freigeistit.notebook.ui.View;
import de.freigeistit.notebook.ui.model.NoteDto;

/**
 * @author Axel P. Meier
 */
public interface NoteFormView extends View
{
    void setObserver(final Observer observer);

    void setNote(final NoteDto note);

    NoteDto getNote();

    interface Observer
    {
        void onSave();

        void onCancel();
    }
}
