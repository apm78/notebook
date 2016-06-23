package de.akquinet.trainings.vaadin.framework.backend;

import java.util.List;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface NoteDao
{
    List<Note> getNotes();

    int getNoteCount();

    void deleteNote(Note note);

    Note updateNote(Note note);

    List<Note> getNotesSortedByDateAsc();
}
