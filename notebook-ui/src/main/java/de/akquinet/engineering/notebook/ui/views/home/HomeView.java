package de.akquinet.engineering.notebook.ui.views.home;

import de.akquinet.engineering.notebook.ui.View;
import de.akquinet.engineering.notebook.ui.model.NoteDto;

import java.util.Collection;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface HomeView extends View
{
    void setObserver(Observer observer);

    void setNotes(Collection<NoteDto> notes);

    void showEditor(final NoteDto note);

    void closeEditor();

    NoteDto getNote();

    interface Observer
    {
        void onSave();

        void onCancel();

        void onEdit(long noteId);
    }
}
