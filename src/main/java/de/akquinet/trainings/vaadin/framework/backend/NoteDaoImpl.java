package de.akquinet.trainings.vaadin.framework.backend;

import com.vaadin.cdi.UIScoped;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a Dao that fakes the access to a real database.
 *
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class NoteDaoImpl implements NoteDao
{
    private long idCounter = 0L;

    private List<Note> notes = new ArrayList<>();
    private ZoneId zoneId;

    public NoteDaoImpl(){
        zoneId = ZoneId.of("Europe/Paris");
        notes.add(new Note(nextId(), "Laundry", "Do the laundry.", ZonedDateTime.now(zoneId)));
        notes.add(new Note(nextId(), "Dishes", "Do the dishes.", ZonedDateTime.now(zoneId).plusDays(1)));
        notes.add(new Note(nextId(), "TV time", "Watch tv.", ZonedDateTime.now(zoneId).plusHours(4)));
        notes.add(new Note(nextId(), "Work", "Go to work.", ZonedDateTime.now(zoneId).minusHours(4)));
    }

    @Override
    public List<Note> getNotes()
    {
        return notes;
    }

    @Override
    public int getNoteCount()
    {
        return notes.size();
    }

    private int findNoteIdxById(final long id)
    {
        for (int i=0; i<notes.size(); ++i){
            if (notes.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    public Note findNoteById(final long id)
    {
        return findNoteByIdImpl(id);
    }

    private Note findNoteByIdImpl(final long id)
    {
        for (final Note n : notes){
            if (n.getId() == id){
                return n;
            }
        }
        return null;
    }

    private long nextId(){
        return ++idCounter;
    }

    private boolean containsNote(final Note note)
    {
        return note.getId() != null && findNoteByIdImpl(note.getId()) != null;

    }

    @Override
    public void deleteNote(final Note note)
    {
        notes.remove(note);
    }

    @Override
    public Note updateNote(final Note note)
    {
        if (containsNote(note))
        {
            final Note n = findNoteByIdImpl(note.getId());
            n.setTitle(note.getTitle());
            n.setDescription(note.getDescription());
            n.setTime(note.getTime());
            return n;
        }

        note.setId(nextId());
        notes.add(note);
        return note;
    }

    @Override
    public List<Note> getNotesSortedByDateAsc()
    {
        final List<Note> sortedList = new ArrayList<>(notes.stream()
                .filter(note -> note.getTime().isAfter(ZonedDateTime.now(zoneId).minusHours(1)))
                .collect(Collectors.toList()));
        sortedList.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        return sortedList;
    }
}
