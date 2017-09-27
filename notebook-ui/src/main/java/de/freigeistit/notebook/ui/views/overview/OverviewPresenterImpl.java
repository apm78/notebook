package de.freigeistit.notebook.ui.views.overview;

import com.vaadin.cdi.UIScoped;
import de.freigeistit.notebook.ui.View;
import de.freigeistit.notebook.ui.model.NoteModel;
import de.freigeistit.notebook.ui.model.NoteDto;
import de.freigeistit.notebook.ui.views.noteform.NoteFormPresenter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Axel P. Meier
 */
@UIScoped
public class OverviewPresenterImpl implements OverviewPresenter, OverviewView.Observer, NoteFormPresenter.Observer
{
    @Inject
    private OverviewView view;

    @Inject
    private NoteFormPresenter noteFormPresenter;

    @Inject
    private NoteModel noteModel;

    @PostConstruct
    public void init(){
        noteFormPresenter.setObserver(this);
    }

    @Override
    public void onEnter()
    {
        view.setObserver(this);
        resetView();
    }

    @Override
    public void onSelect(final NoteDto selectedNote)
    {
        showNote(selectedNote);
    }

    private void showNote(final NoteDto note){
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
        showNote(new NoteDto());
    }

    @Override
    public void onDelete(final NoteDto note)
    {
        noteModel.deleteNote(note);

        resetView();
    }

    @Override
    public View getView()
    {
        return view;
    }

    private void resetView(){
        view.setNotes(noteModel.getNotes());
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
