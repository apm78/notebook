package de.akquinet.engineering.notebook.ui.views.noteform;

import de.akquinet.engineering.notebook.ui.model.NoteDto;
import de.akquinet.engineering.notebook.ui.model.NoteModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;


/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NoteFormPresenterImplTest
{
    @Mock
    private NoteModel noteModel;

    @Mock
    private NoteFormView noteFormView;

    @InjectMocks
    private NoteFormPresenterImpl noteFormPresenter;

    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
    }

    private static NoteDto createDummyNote()
    {
        final NoteDto note = new NoteDto();
        note.setTitle("Dummy Note");
        note.setDescription("This is a dummy note.");
        note.setTime(LocalDateTime.of(2016, 12, 2, 12, 36, 0));
        return note;
    }

    @Test
    public void testOnSave()
    {
        final NoteDto note = createDummyNote();
        final NoteFormPresenter.Observer observer = Mockito.mock(NoteFormPresenter.Observer.class);

        Mockito.when(noteFormView.getNote())
                .thenReturn(note);

        Mockito.when(noteModel.updateNote(note))
                .thenReturn(note);

        noteFormPresenter.setObserver(observer);

        noteFormPresenter.onSave();
        Mockito.verify(noteFormView).getNote();
        Mockito.verify(noteModel).updateNote(note);
        Mockito.verify(noteFormView).setNote(note);
        Mockito.verify(observer).onSave();

        Mockito.verifyNoMoreInteractions(noteFormView, noteModel, observer);
    }

    @Test
    public void testOnCancel(){
        final NoteFormPresenter.Observer observer = Mockito.mock(NoteFormPresenter.Observer.class);
        noteFormPresenter.setObserver(observer);
        noteFormPresenter.onCancel();
        Mockito.verify(observer).onCancel();

        Mockito.verifyNoMoreInteractions(noteFormView, noteModel, observer);
    }

    @Test
    public void testSetNote()
    {
        final NoteDto note = createDummyNote();
        noteFormPresenter.setNote(note);
        Mockito.verify(noteFormView).setNote(note);
        Mockito.verify(noteFormView).setObserver(noteFormPresenter);
        Mockito.verifyNoMoreInteractions(noteFormView, noteModel);
    }
}
