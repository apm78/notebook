package de.freigeistit.notebook.ui.model;

import de.freigeistit.notebook.datasource.dao.NoteDao;
import de.freigeistit.notebook.datasource.entity.Note;
import de.freigeistit.notebook.datasource.util.DateTimeConverter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Axel P. Meier
 */
@Stateless
public class NoteModelImpl implements NoteModel
{
    @Resource
    private SessionContext sessionContext;

    @Inject
    private NoteDao noteDao;

    private String getUserLogin()
    {
        if (sessionContext != null
                && sessionContext.getCallerPrincipal() != null)
        {
            return sessionContext.getCallerPrincipal().getName();
        }
        return "";
    }

    @Override
    public List<NoteDto> getNotes()
    {
        return toNoteDtoList(noteDao.getNotes(getUserLogin()));
    }

    private static List<NoteDto> toNoteDtoList(final List<Note> noteList){
        return noteList
                .stream()
                .map(NoteDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public int getNoteCount()
    {
        return noteDao.getNoteCount(getUserLogin());
    }

    @Override
    public void deleteNote(final NoteDto note)
    {
        noteDao.deleteNote(note.getId(), getUserLogin());
    }

    @Override
    public NoteDto updateNote(final NoteDto note)
    {
        return new NoteDto(noteDao.updateNote(note.toNote(), getUserLogin()));
    }

    @Override
    public List<NoteDto> getNotesSortedByDateAscNotOlderThan(final LocalDateTime dateTime)
    {
        return toNoteDtoList(noteDao
                .getNotesSortedByDateAscNotThan(getUserLogin(), DateTimeConverter.toDate(dateTime)));
    }

    @Override
    public NoteDto findNoteById(final long id)
    {
        return new NoteDto(noteDao.findNoteById(id, getUserLogin()));
    }
}
