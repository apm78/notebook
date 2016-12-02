package de.akquinet.engineering.notebook.datasource.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@Entity
public class Note extends AbstractEntity
{
    @Basic
    private String title = "";

    @Basic
    private String description = "";

    @Basic
    private Date time;

    public Note()
    {

    }

    public Note(final String title, final String description, final Date time)
    {
        this.title = title;
        this.description = description;
        setTime(time);
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

    public Date getTime()
    {
        return time;
    }

    public void setTime(final Date time)
    {
        this.time = new Date(time.getTime());
    }

    @Override
    public String toString()
    {
        return "Note{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
