package de.akquinet.trainings.vaadin.framework.backend;

import java.time.LocalDateTime;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class Note
{
    private Long id;
    private String title;
    private String description;
    private LocalDateTime time;

    public Note()
    {

    }

    public Note(final long id, final String title, final String description, LocalDateTime time)
    {
        this.title = title;
        this.description = description;
        this.id = id;
        this.time = time;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final long id)
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
