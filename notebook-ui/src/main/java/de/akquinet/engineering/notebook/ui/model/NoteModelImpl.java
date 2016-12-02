package de.akquinet.engineering.notebook.ui.model;

import de.akquinet.engineering.notebook.datasource.entity.Note;
import de.akquinet.engineering.notebook.datasource.util.DateTimeConverter;

import javax.annotation.Resource;
import javax.ejb.PostActivate;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@Stateful
@SessionScoped
public class NoteModelImpl implements NoteModel, Serializable
{
    @Resource
    private SessionContext sessionContext;

    private final List<NoteDto> noteList = new ArrayList<>();

    public NoteModelImpl()
    {
        init();
    }

    private void init(){
        noteList.clear();
        addNote(new NoteDto(new Note("Laundry", "Do the laundry.", DateTimeConverter.toDate(LocalDateTime.now()))));
        addNote(new NoteDto(new Note("Dishes", "Do the dishes.", DateTimeConverter.toDate(LocalDateTime.now().plusDays(1)))));
        addNote(new NoteDto(new Note("TV time", "Watch tv.", DateTimeConverter.toDate(LocalDateTime.now().plusHours(4)))));
        addNote(new NoteDto(new Note("Work", "Go to work.", DateTimeConverter.toDate(LocalDateTime.now().minusHours(4)))));
    }

    @PostActivate
    public void postActivate()
    {
        init();
    }

    // Use this as the userId
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
        return noteList;
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
        return noteList.size();
    }

    @Override
    public void deleteNote(final NoteDto note)
    {
        if (note.getId() != null)
        {
            final NoteDto noteDto = findNoteById(note.getId());
            if (noteDto != null)
            {
                noteList.remove(noteDto);
            }
        }
    }

    @Override
    public NoteDto updateNote(final NoteDto note)
    {
        if (note.getId() != null){
            deleteNote(note);
            noteList.add(note);
        }
        else
        {
            addNote(note);
        }
        return note;
    }

    private void addNote(final NoteDto noteDto){
        noteDto.setId((long) noteList.size());
        noteList.add(noteDto);
    }

    @Override
    public List<NoteDto> getNotesSortedByDateAscNotOlderThan(final LocalDateTime dateTime)
    {
        final List<NoteDto> sortedList = new ArrayList<>(noteList.stream()
                .filter(note -> note.getTime().isAfter(dateTime))
                .collect(Collectors.toList()));
        sortedList.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        return sortedList;
    }

    @Override
    public NoteDto findNoteById(final long id)
    {
        for (final NoteDto note : noteList)
        {
            if (note.getId() != null
                    && id == note.getId())
            {
                return note;
            }
        }
        return null;
    }
}
