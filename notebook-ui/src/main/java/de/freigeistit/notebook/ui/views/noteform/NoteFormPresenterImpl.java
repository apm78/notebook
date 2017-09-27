package de.freigeistit.notebook.ui.views.noteform;

import de.freigeistit.notebook.ui.View;
import de.freigeistit.notebook.ui.model.NoteDto;
import de.freigeistit.notebook.ui.model.NoteModel;

import javax.inject.Inject;

/**
 * @author Axel P. Meier
 */
public class NoteFormPresenterImpl implements NoteFormPresenter, NoteFormView.Observer
{
    @Inject
    private NoteModel noteModel;

    @Inject
    private NoteFormView noteFormView;

    private Observer observer;

    @Override
    public void setNote(final NoteDto note)
    {
        noteFormView.setObserver(this);
        noteFormView.setNote(note);
    }

    @Override
    public void onSave()
    {
        final NoteDto note = noteFormView.getNote();
        final NoteDto updatedNote = noteModel.updateNote(note);
        noteFormView.setNote(updatedNote);

        if (observer != null)
        {
            observer.onSave();
        }
    }

    @Override
    public void onCancel()
    {
        observer.onCancel();
    }

    @Override
    public View getView()
    {
        return noteFormView;
    }

    @Override
    public void setObserver(final Observer observer)
    {
        this.observer = observer;
    }
}
