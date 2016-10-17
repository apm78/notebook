package de.akquinet.engineering.notebook.ui.views.overview;

import com.vaadin.cdi.UIScoped;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;
import de.akquinet.engineering.notebook.ui.View;
import de.akquinet.engineering.notebook.ui.model.NoteModel;

import javax.inject.Inject;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class OverviewPresenterImpl implements OverviewPresenter, OverviewView.Observer
{
    @Inject
    private OverviewView view;

    @Inject
    private NoteModel noteModel;

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
            view.showEditor(note);
        }
        else{
            view.showEditor(null);
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
        view.showEditor(null);
        view.selectNote(null);
    }

    @Override
    public void onSave()
    {
        final NoteDto note = view.getNote();
        noteModel.updateNote(note);

        resetView();
    }
}
