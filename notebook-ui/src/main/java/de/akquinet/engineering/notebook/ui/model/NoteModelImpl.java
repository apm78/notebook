package de.akquinet.engineering.notebook.ui.model;

import de.akquinet.engineering.notebook.datasource.dao.NoteDao;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.security.Principal;
import java.util.List;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@Stateless
public class NoteModelImpl implements NoteModel
{
    @Context
    private HttpServletRequest request;

    @Inject
    private NoteDao noteDao;

    private String getUserLogin(){
        final Principal principal = null;//request.getUserPrincipal();
        if (principal != null){
            return principal.getName();
        }
        return "";
    }

    @Override
    public List<NoteDto> getNotes()
    {
        return noteDao.getNotes(getUserLogin());
    }

    @Override
    public int getNoteCount()
    {
        return noteDao.getNoteCount(getUserLogin());
    }

    @Override
    public void deleteNote(final NoteDto note)
    {
        noteDao.deleteNote(note, getUserLogin());
    }

    @Override
    public NoteDto updateNote(final NoteDto note)
    {
        return noteDao.updateNote(note, getUserLogin());
    }

    @Override
    public List<NoteDto> getNotesSortedByDateAscNotOlderHour(final long hour)
    {
        return noteDao.getNotesSortedByDateAscNotOlderHour(getUserLogin(), hour);
    }

    @Override
    public NoteDto findNoteById(final long id)
    {
        return noteDao.findNoteById(id, getUserLogin());
    }
}
