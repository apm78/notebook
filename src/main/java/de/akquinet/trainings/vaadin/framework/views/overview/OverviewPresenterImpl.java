package de.akquinet.trainings.vaadin.framework.views.overview;

import com.vaadin.cdi.UIScoped;
import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.NoteDao;

import javax.inject.Inject;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class OverviewPresenterImpl implements OverviewPresenter
{
    @Inject
    private OverviewView view;

    @Inject
    private NoteDao noteDao;

    @Override
    public void onEnter()
    {
        view.setNotes(noteDao.getNotes());
    }

    @Override
    public View getView()
    {
        return view;
    }
}
