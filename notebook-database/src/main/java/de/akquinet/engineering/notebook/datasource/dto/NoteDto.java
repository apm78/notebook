package de.akquinet.engineering.notebook.datasource.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NoteDto
{
    private Long id;
    
    private String title;

    private String description;

    private LocalDateTime time;

    public NoteDto()
    {

    }

    public NoteDto(final Long id, final String title, final String description, LocalDateTime time)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public NoteDto(final Long id, final String title, final String description, Date time)
    {
        this(id, title, description,
                LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault()));
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    public void setTime(final LocalDateTime time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
