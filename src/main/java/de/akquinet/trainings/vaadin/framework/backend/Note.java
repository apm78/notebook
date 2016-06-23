package de.akquinet.trainings.vaadin.framework.backend;

import java.time.ZonedDateTime;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class Note
{
    private Long id;
    private String title;
    private String description;
    private ZonedDateTime time;

    public Note()
    {

    }

    public Note(final long id, final String title, final String description, ZonedDateTime time)
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

    public ZonedDateTime getTime()
    {
        return time;
    }

    public void setTime(final ZonedDateTime time)
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
