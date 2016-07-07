package de.akquinet.engineering.notebook.ui.views.home;

import com.vaadin.cdi.UIScoped;
import de.akquinet.engineering.notebook.ui.View;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;
import de.akquinet.engineering.notebook.datasource.dao.NoteDao;
import de.akquinet.engineering.notebook.ui.views.noteform.NoteFormPresenter;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class HomePresenterImpl implements HomePresenter, HomeView.Observer, NoteFormPresenter.Observer
{
    @Inject
    private HomeView homeView;

    @Inject
    private NoteDao noteDao;

    @Override
    public void onEnter()
    {
        homeView.setObserver(this);
        homeView.setNotes(noteDao.getNotesSortedByDateAsc(""));
    }

    @Override
    public View getView()
    {
        return homeView;
    }

    @Override
    public void onEdit(final long noteId)
    {
        final NoteFormPresenter noteFormPresenter = CDI.current().select(NoteFormPresenter.class).get();
        noteFormPresenter.setObserver(this);
        final NoteDto note = noteDao.findNoteById(noteId, "");
        if (note != null){
            noteFormPresenter.setNote(note);
            homeView.showEditor(noteFormPresenter.getView());
        }
    }

    @Override
    public void onSave()
    {
        homeView.closeEditor();
        homeView.setNotes(noteDao.getNotesSortedByDateAsc(""));
    }

    @Override
    public void onCancel()
    {
        homeView.closeEditor();
    }
}
