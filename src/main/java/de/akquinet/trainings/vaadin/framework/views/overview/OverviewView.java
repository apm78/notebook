package de.akquinet.trainings.vaadin.framework.views.overview;

import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;

import java.util.Collection;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface OverviewView extends View
{
    void setNotes(Collection<Note> notes);
}
