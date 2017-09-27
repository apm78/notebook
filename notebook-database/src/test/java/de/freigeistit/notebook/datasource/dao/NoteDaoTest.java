package de.freigeistit.notebook.datasource.dao;

import de.freigeistit.notebook.datasource.entity.Note;
import de.freigeistit.notebook.datasource.entity.Notebook;
import de.freigeistit.notebook.datasource.util.DateTimeConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

/**
 * @author Axel P. Meier
 */
public class NoteDaoTest
{
    @Mock
    private EntityManager entityManager;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private NoteDaoImpl noteDao;

    private final Notebook notebook = new Notebook();

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);

        // mock entity manager
        final TypedQuery queryMock = Mockito.mock(TypedQuery.class);
        Mockito.when(queryMock.setParameter(anyString(), anyObject()))
                .thenReturn(queryMock);
        Mockito.when(queryMock.getSingleResult())
                .thenReturn(notebook);
        Mockito.when(entityManager.createNamedQuery(anyString(),
                any(Class.class)))
                .thenReturn(queryMock);
    }

    @Test
    public void testGetNotesSortedByDateAscNotOlderHour(){

        final Note noteA = new Note("A", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 14, 39)));
        final Note noteB = new Note("B", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 14, 55)));
        final Note noteC = new Note("C", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 15, 39)));

        notebook.addNote(noteA);
        notebook.addNote(noteB);
        notebook.addNote(noteC);

        final List<Note> noteDtoList = noteDao.getNotesSortedByDateAscNotThan("user",
                DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 14, 43)));
        Assert.assertEquals(2, noteDtoList.size());
        Assert.assertEquals("B", noteDtoList.get(0).getTitle());
        Assert.assertEquals("C", noteDtoList.get(1).getTitle());
    }

    @Test
    public void testDeleteNote()
    {
        final Note noteA = new Note("A", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 14, 39)));
        noteA.setId(1L);
        final Note noteB = new Note("B", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 14, 55)));
        noteB.setId(2L);
        final Note noteC = new Note("C", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 15, 39)));
        noteC.setId(3L);

        notebook.addNote(noteA);
        notebook.addNote(noteB);
        notebook.addNote(noteC);

        noteDao.deleteNote(1L, "user");
        Assert.assertEquals(2, notebook.getNotes().size());
        Assert.assertEquals(noteB, notebook.getNotes().get(0));
        Assert.assertEquals(noteC, notebook.getNotes().get(1));
    }
}
