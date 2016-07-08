package de.akquinet.engineering.notebook.ui.model;

import de.akquinet.engineering.notebook.datasource.dto.NoteDto;

import java.util.List;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface NoteModel
{
    List<NoteDto> getNotes();

    int getNoteCount();

    void deleteNote(NoteDto note);

    NoteDto updateNote(NoteDto note);

    List<NoteDto> getNotesSortedByDateAscNotOlderHour(long hour);

    NoteDto findNoteById(long id);
}
