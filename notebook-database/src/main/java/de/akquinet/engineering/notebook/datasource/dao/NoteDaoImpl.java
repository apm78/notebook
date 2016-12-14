package de.akquinet.engineering.notebook.datasource.dao;

import de.akquinet.engineering.notebook.datasource.entity.Note;
import de.akquinet.engineering.notebook.datasource.entity.Notebook;
import de.akquinet.engineering.notebook.datasource.entity.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a Dao that fakes the access to a real database.
 *
 * @author Axel Meier, akquinet engineering GmbH
 */
// TODO: make this a stateless bean
public class NoteDaoImpl implements NoteDao
{
    // TODO: add entity manager

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
        // TODO: call createNamedQuery Notebook.FIND_NOTEBOOK_BY_USER_ID with userId
        return null;
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
        // TODO
    }

    private Note mergeNote(final Note note)
    {
        // TODO
        return null;
    }

    private boolean exists(final Note note)
    {
        if (note == null || note.getId() == null){
            return false;
        }
        // TODO: call entity manager's find method
        return false;
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
            // TODO: persist notebook
        }

        notebook.addNote(note);
        // TODO: persist note
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
