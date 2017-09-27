package de.freigeistit.notebook.datasource.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQuery(name = Notebook.FIND_NOTEBOOK_BY_USER_ID,
        query = "select nb from Notebook nb where nb.user.login = :userId")
public class Notebook extends AbstractEntity
{

    public static final String FIND_NOTEBOOK_BY_USER_ID = "Notebook.findNotebookByUserId";

    @ManyToOne
    private User user;

    @OneToMany
    @OrderBy("time DESC")
    private List<Note> notes = new ArrayList<>();

    public Notebook()
    {
    }

    public Notebook(final User user)
    {
        this.user = user;
    }

    public List<Note> getNotes()
    {
        return Collections.unmodifiableList(notes);
    }

    public void setNotes(final List<Note> newNoteList)
    {
        notes = new ArrayList<>(newNoteList);
    }

    public void addNote(final Note note)
    {
        notes.add(note);
    }

    public void removeNote(final Note note)
    {
        notes.remove(note);
    }

    public Set<Note> returnOwnNotesNotInList(final List<Note> newNoteList)
    {
        final Set<Note> result = new HashSet<>(notes.size());
        for (final Note task : notes)
        {
            if (!newNoteList.contains(task))
            {
                result.add(task);
            }
        }
        return result;
    }

}
