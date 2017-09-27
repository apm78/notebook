package de.freigeistit.notebook.datasource.dao;

import de.freigeistit.notebook.datasource.entity.Note;
import de.freigeistit.notebook.datasource.entity.Notebook;
import de.freigeistit.notebook.datasource.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a Dao that fakes the access to a real database.
 *
 * @author Axel P. Meier
 */
@Stateless
public class NoteDaoImpl implements NoteDao
{
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserDao userDao;

    @Override
    public List<Note> getNotes(final String userId)
    {
        final Notebook notebook = getNotebook(userId);
        if (notebook == null)
        {
            return Collections.emptyList();
        }

        return notebook.getNotes();
    }

    private List<Note> getNoteList(final String userId)
    {
        final Notebook notebook = getNotebook(userId);
        if (notebook == null)
        {
            return Collections.emptyList();
        }

        return notebook.getNotes();
    }

    private Notebook getNotebook(final String userId)
    {
        try
        {
            return entityManager.createNamedQuery(Notebook.FIND_NOTEBOOK_BY_USER_ID, Notebook.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }
        catch (final NoResultException e)
        {
            return null;
        }
    }

    @Override
    public int getNoteCount(final String userId)
    {
        return getNoteList(userId).size();
    }

    public Note findNoteById(final long id, final String userId)
    {
        return findNoteByUserIdImpl(id, userId);
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

    private Note findNoteByUserIdImpl(final long id, final String userId)
    {
        final Notebook notebook = getNotebook(userId);
        if (notebook == null)
        {
            return null;
        }
        for (final Note note : notebook.getNotes())
        {
            if (note.getId() != null && note.getId() == id)
            {
                return note;
            }
        }
        return null;
    }

    @Override
    public void deleteNote(final long noteId, final String userId)
    {
        final Notebook notebook = getNotebook(userId);
        if (notebook != null)
        {
            final Note noteEntity = findNoteByIdImpl(notebook, noteId);
            if (noteEntity != null)
            {
                notebook.removeNote(noteEntity);
                entityManager.merge(notebook);
            }
        }
    }

    private Note mergeNote(final Note note)
    {
        if (note.getId() == null)
        {
            throw new IllegalArgumentException("A note must have a valid ID to merge");
        }

        return entityManager.merge(note);
    }

    private boolean exists(final Note note)
    {
        if (note == null || note.getId() == null){
            return false;
        }
        return entityManager.find(Note.class, note.getId()) != null;
    }

    @Override
    public Note updateNote(final Note note, final String userId)
    {
        if (exists(note))
        {
            return mergeNote(note);
        }

        return persistNote(note, userId);
    }

    private Note persistNote(final Note note, final String userId)
    {
        Notebook notebook = getNotebook(userId);
        if (notebook == null)
        {
            final User user = findUser(userId);
            if (user == null)
            {
                return null;
            }
            notebook = new Notebook(user);
            entityManager.persist(notebook);
        }

        notebook.addNote(note);
        entityManager.persist(note);
        return note;
    }

    private User findUser(final String login)
    {
        return userDao.findUserByLogin(login);
    }

    @Override
    public List<Note> getNotesSortedByDateAscNotThan(final String userId,
                                                     final Date localDateTime)
    {
        final List<Note> sortedList = new ArrayList<>(getNotes(userId).stream()
                .filter(note -> note.getTime().after(localDateTime))
                .collect(Collectors.toList()));
        sortedList.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        return sortedList;
    }
}
