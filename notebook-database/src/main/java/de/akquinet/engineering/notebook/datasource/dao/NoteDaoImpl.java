package de.akquinet.engineering.notebook.datasource.dao;

import de.akquinet.engineering.notebook.datasource.dto.NoteDto;
import de.akquinet.engineering.notebook.datasource.entity.Note;
import de.akquinet.engineering.notebook.datasource.entity.Notebook;
import de.akquinet.engineering.notebook.datasource.util.DateTimeConverter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        final Notebook notebook = getNotebook(userId);
        if (notebook == null){
            return Collections.emptyList();
        }

        return notebook.getNotes().stream()
                .map(NoteDto::new)
                .collect(Collectors.toList());
    }

    private List<Note> getNoteList(final String userId){
        final Notebook notebook = getNotebook(userId);
        if (notebook == null){
            return Collections.emptyList();
        }

        return notebook.getNotes();
    }

    private Notebook getNotebook(final String userId)
    {
        final List<Notebook> notebooks = entityManager.createNamedQuery(Notebook.FIND_NOTEBOOK_BY_USER_ID, Notebook.class)
                .setParameter("userId", userId)
                .getResultList();
        if (notebooks.isEmpty()){
            return null;
        }
        return notebooks.get(0);
    }

    @Override
    public int getNoteCount(final String userId)
    {
        return getNoteList(userId).size();
    }

    public NoteDto findNoteById(final long id, final String userId)
    {
        return findNoteDtoByIdImpl(id, userId);
    }

    private Note findNoteByIdImpl(final Notebook notebook,
            final long id)
    {
        for (final Note note : notebook.getNotes())
        {
            if (note.getId() != null && note.getId() == id)
            {
                return note;
            }
        }
        return null;
    }

    private NoteDto findNoteDtoByIdImpl(final long id, final String userId)
    {
        final Notebook notebook = getNotebook(userId);
        if (notebook == null){
            return null;
        }
        for (final Note note : notebook.getNotes())
        {
            if (note.getId() != null && note.getId() == id)
            {
                return new NoteDto(note);
            }
        }
        return null;
    }

    @Override
    public void deleteNote(final NoteDto note, final String userId)
    {
        final Notebook notebook = getNotebook(userId);
        if (notebook != null)
        {
            final Note noteEntity = findNoteByIdImpl(notebook, note.getId());
            if (noteEntity != null)
            {
                notebook.getNotes().remove(noteEntity);
                entityManager.merge(notebook);
            }
        }
    }

    @Override
    public NoteDto updateNote(final NoteDto note, final String userId)
    {
        if (note.getId() != null)
        {
            final Note noteEntity = entityManager.find(Note.class, note.getId());
            if (noteEntity != null)
            {
                noteEntity.setTitle(note.getTitle());
                noteEntity.setTitle(note.getDescription());
                noteEntity.setTime(DateTimeConverter.toDate(note.getTime()));
                final Note mergedEntity = entityManager.merge(noteEntity);
                return new NoteDto(mergedEntity);
            }
        }

        final Note newNote = new Note(note.getTitle(), note.getDescription(), DateTimeConverter.toDate(note.getTime()));
        Notebook notebook = getNotebook(userId);
        if (notebook == null){
            return null;
        }
//        if (notebook == null){
//            notebook = new Notebook()
//        }
        if (notebook != null)
        {
            notebook.addNote(newNote);
        }
        entityManager.persist(newNote);
        return new NoteDto(newNote);
    }

    @Override
    public List<NoteDto> getNotesSortedByDateAscNotOlderHour(final String userId,
            long hour)
    {
        final List<NoteDto> sortedList = new ArrayList<>(getNotes(userId).stream()
                .filter(note -> note.getTime().isAfter(LocalDateTime.now().minusHours(hour)))
                .collect(Collectors.toList()));
        sortedList.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        return sortedList;
    }
}
