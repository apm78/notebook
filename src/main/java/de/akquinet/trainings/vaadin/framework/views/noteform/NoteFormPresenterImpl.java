package de.akquinet.trainings.vaadin.framework.views.noteform;

import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;
import de.akquinet.trainings.vaadin.framework.backend.NoteDao;

import javax.inject.Inject;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NoteFormPresenterImpl implements NoteFormPresenter, NoteFormView.Observer
{
    @Inject
    private NoteDao noteDao;

    @Inject
    private NoteFormView noteFormView;

    @Override
    public void onSave()
    {
        final Note note = noteFormView.getNote();
        final Note updatedNote = noteDao.updateNote(note);
        noteFormView.setNote(updatedNote);
    }

    @Override
    public void onCancel()
    {

    }

    @Override
    public View getView()
    {
        return noteFormView;
    }
}
