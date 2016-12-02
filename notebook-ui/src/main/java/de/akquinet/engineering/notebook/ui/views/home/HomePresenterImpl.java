package de.akquinet.engineering.notebook.ui.views.home;

import com.vaadin.cdi.UIScoped;
import de.akquinet.engineering.notebook.ui.model.NoteDto;
import de.akquinet.engineering.notebook.ui.View;
import de.akquinet.engineering.notebook.ui.model.NoteModel;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class HomePresenterImpl implements HomePresenter, HomeView.Observer
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
        final NoteDto note = noteModel.findNoteById(noteId);
        if (note != null){

            homeView.showEditor(note);
        }
    }

    @Override
    public void onSave()
    {
        final NoteDto note = homeView.getNote();
        noteModel.updateNote(note);

        homeView.closeEditor();
        homeView.setNotes(getNotesSortedByDateAscNotOlder1Hour());
    }

    private List<NoteDto> getNotesSortedByDateAscNotOlder1Hour()
    {
        return noteModel.getNotesSortedByDateAscNotOlderThan(LocalDateTime.now().minusHours(1));
    }

    @Override
    public void onCancel()
    {
        homeView.closeEditor();
    }
}
