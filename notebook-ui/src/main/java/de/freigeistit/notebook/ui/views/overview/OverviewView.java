package de.freigeistit.notebook.ui.views.overview;

import de.freigeistit.notebook.ui.View;
import de.freigeistit.notebook.ui.model.NoteDto;

import java.util.Collection;

/**
 * @author Axel P. Meier
 */
public interface OverviewView extends View
{
    void setObserver(Observer observer);

    void selectNote(final NoteDto note);

    void setNotes(Collection<NoteDto> notes);

    void setEditorView(View view);

    void setEditorVisible(boolean visible);

    interface Observer {
        void onSelect(NoteDto selectedNote);

        void onAdd();

        void onDelete(NoteDto note);
    }
}
