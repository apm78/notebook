package de.freigeistit.notebook.ui.views.home;

import de.freigeistit.notebook.ui.View;
import de.freigeistit.notebook.ui.model.NoteDto;

import java.util.Collection;

/**
 * @author Axel P. Meier
 */
public interface HomeView extends View
{
    void setObserver(Observer observer);

    void setNotes(Collection<NoteDto> notes);

    void showEditor(View editView);

    void closeEditor();

    interface Observer
    {
        void onEdit(long noteId);
    }
}
