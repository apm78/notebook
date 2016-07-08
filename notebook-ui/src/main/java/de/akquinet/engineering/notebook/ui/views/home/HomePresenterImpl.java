package de.akquinet.engineering.notebook.ui.views.home;

import com.vaadin.cdi.UIScoped;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;
import de.akquinet.engineering.notebook.ui.View;
import de.akquinet.engineering.notebook.ui.model.NoteModel;
import de.akquinet.engineering.notebook.ui.views.noteform.NoteFormPresenter;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class HomePresenterImpl implements HomePresenter, HomeView.Observer, NoteFormPresenter.Observer
{
    @Inject
    private HomeView homeView;

    @Inject
    private NoteModel noteModel;

    @Override
    public void onEnter()
    {
        homeView.setObserver(this);
        homeView.setNotes(getNotesSortedByDateAscNotOlder1Hour());
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
        final NoteDto note = noteModel.findNoteById(noteId);
        if (note != null){
            noteFormPresenter.setNote(note);
            homeView.showEditor(noteFormPresenter.getView());
        }
    }

    @Override
    public void onSave()
    {
        homeView.closeEditor();
        homeView.setNotes(getNotesSortedByDateAscNotOlder1Hour());
    }

    private List<NoteDto> getNotesSortedByDateAscNotOlder1Hour()
    {
        return noteModel.getNotesSortedByDateAscNotOlderHour(1);
    }

    @Override
    public void onCancel()
    {
        homeView.closeEditor();
    }
}