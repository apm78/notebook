package de.akquinet.engineering.notebook.datasource.dao;

import de.akquinet.engineering.notebook.datasource.dto.NoteDto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a Dao that fakes the access to a real database.
 *
 * @author Axel Meier, akquinet engineering GmbH
 */
@Stateless
public class NoteDaoImpl implements NoteDao
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<NoteDto> getNotes(final String userId)
    {
        return new ArrayList<>();
//        return entityManager.createNamedQuery("", Note.class);
    }

    @Override
    public int getNoteCount(final String userId)
    {
        return 0;
    }

    private int findNoteIdxById(final long id)
    {
        return -1;
    }

    public NoteDto findNoteById(final long id, final String userId)
    {
        return findNoteByIdImpl(id);
    }

    private NoteDto findNoteByIdImpl(final long id)
    {
        return null;
    }

    private boolean containsNote(final NoteDto note)
    {
        return note.getId() != null && findNoteByIdImpl(note.getId()) != null;

    }

    @Override
    public void deleteNote(final NoteDto note, final String userId)
    {

    }

    @Override
    public NoteDto updateNote(final NoteDto note, final String userId)
    {
        return note;
    }

    @Override
    public List<NoteDto> getNotesSortedByDateAsc(final String userId)
    {
        return new ArrayList<>();
//        final List<NoteDto> sortedList = new ArrayList<>(notes.stream()
//                .filter(note -> note.getTime().isAfter(LocalDateTime.now(zoneId).minusHours(1)))
//                .collect(Collectors.toList()));
//        sortedList.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
//        return sortedList;
    }
}
