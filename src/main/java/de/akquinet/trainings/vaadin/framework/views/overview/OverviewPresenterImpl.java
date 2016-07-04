package de.akquinet.trainings.vaadin.framework.views.overview;

import com.vaadin.cdi.UIScoped;
import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;
import de.akquinet.trainings.vaadin.framework.backend.NoteDao;
import de.akquinet.trainings.vaadin.framework.views.noteform.NoteFormPresenter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class OverviewPresenterImpl implements OverviewPresenter, OverviewView.Observer, NoteFormPresenter.Observer
{
    @Inject
    private OverviewView view;

    @Inject
    private NoteFormPresenter noteFormPresenter;

    @Inject
    private NoteDao noteDao;

    @PostConstruct
    public void init(){
        noteFormPresenter.setObserver(this);
    }

    @Override
    public void onEnter()
    {
        // TODO handle parameter for showing table entry
        view.setObserver(this);
        resetView();
    }

    @Override
    public void onSelect(final Note selectedNote)
    {
        showNote(selectedNote);
    }

    private void showNote(final Note note){
        if (note != null)
        {
            noteFormPresenter.setNote(note);
            view.setEditorView(noteFormPresenter.getView());
            view.setEditorVisible(true);
        }
        else{
            view.setEditorVisible(false);
        }
    }

    @Override
    public void onAdd()
    {
        showNote(new Note());
    }

    @Override
    public void onDelete(final Note note)
    {
        noteDao.deleteNote(note);
        resetView();
    }

    @Override
    public View getView()
    {
        return view;
    }

    private void resetView(){
        view.setNotes(noteDao.getNotes());
        view.setEditorVisible(false);
        view.selectNote(null);
    }

    @Override
    public void onSave()
    {
        resetView();
    }

    @Override
    public void onCancel()
    {

    }
}
