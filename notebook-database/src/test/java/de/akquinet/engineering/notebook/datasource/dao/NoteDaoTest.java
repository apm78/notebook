/*
 * WEAT EABR
 */
package de.akquinet.engineering.notebook.datasource.dao;

import de.akquinet.engineering.notebook.datasource.dto.NoteDto;
import de.akquinet.engineering.notebook.datasource.entity.Note;
import de.akquinet.engineering.notebook.datasource.entity.Notebook;
import de.akquinet.engineering.notebook.datasource.util.DateTimeConverter;
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
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NoteDaoTest
{
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private NoteDaoImpl noteDao;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNotesSortedByDateAscNotOlderHour(){

        final Note noteA = new Note("A", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 14, 39)));
        final Note noteB = new Note("B", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 14, 55)));
        final Note noteC = new Note("C", "", DateTimeConverter.toDate(LocalDateTime.of(2016, 7, 11, 15, 39)));

        final Notebook notebook = new Notebook();
        notebook.addNote(noteA);
        notebook.addNote(noteB);
        notebook.addNote(noteC);
        final TypedQuery queryMock = Mockito.mock(TypedQuery.class);
        Mockito.when(queryMock.setParameter(anyString(), anyObject()))
                .thenReturn(queryMock);
        Mockito.when(queryMock.getSingleResult())
                .thenReturn(notebook);

        Mockito.when(entityManager.createNamedQuery(anyString(),
                any(Class.class)))
                .thenReturn(queryMock);

        final List<NoteDto> noteDtoList = noteDao.getNotesSortedByDateAscNotThan("user", LocalDateTime.of(2016, 7, 11, 14, 43));
        Assert.assertEquals(2, noteDtoList.size());
        Assert.assertEquals("B", noteDtoList.get(0).getTitle());
        Assert.assertEquals("C", noteDtoList.get(1).getTitle());
    }
}
