package de.freigeistit.notebook.datasource.dao;

import de.freigeistit.notebook.datasource.entity.Note;

import java.util.Date;
import java.util.List;

/**
 * @author Axel P. Meier
 */
public interface NoteDao
{
    List<Note> getNotes(String userId);

    int getNoteCount(String userId);

    void deleteNote(long noteId, String userId);

    Note updateNote(Note note, String userId);

    List<Note> getNotesSortedByDateAscNotThan(String userId, Date localDateTime);

    Note findNoteById(long id, String userId);
}
