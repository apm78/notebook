package de.akquinet.engineering.notebook.ui.views.noteform;

import de.akquinet.engineering.notebook.ui.View;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;

/**
 * @author Axel Meier, akquinet engineering GmbH
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
