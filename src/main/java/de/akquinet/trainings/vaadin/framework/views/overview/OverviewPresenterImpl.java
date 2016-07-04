package de.akquinet.trainings.vaadin.framework.views.overview;

import com.vaadin.cdi.UIScoped;
import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;
import de.akquinet.trainings.vaadin.framework.backend.NoteDao;
import de.akquinet.trainings.vaadin.framework.views.noteform.NoteFormPresenter;

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
    private NoteFormPresenter noteFormPresenter;

    @Inject
    private NoteDao noteDao;

    @Override
    public void onEnter()
    {
        // TODO handle parameter for showing table entry
        view.setObserver(this);
        view.setNotes(noteDao.getNotes());
        view.setEditorVisible(false);
    }

    @Override
    public void onSelect(final Note selectedNote)
    {
        if (selectedNote != null)
        {
            noteFormPresenter.setNote(selectedNote);
            view.setEditorView(noteFormPresenter.getView());
            view.setEditorVisible(true);
        }
        else{
            view.setEditorVisible(false);
        }
    }

    @Override
    public View getView()
    {
        return view;
    }
}
