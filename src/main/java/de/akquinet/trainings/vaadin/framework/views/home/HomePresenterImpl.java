package de.akquinet.trainings.vaadin.framework.views.home;

import com.vaadin.cdi.UIScoped;
import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.NoteDao;

import javax.inject.Inject;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class HomePresenterImpl implements HomePresenter
{
    @Inject
    private HomeView homeView;

    @Inject
    private NoteDao noteDao;

    @Override
    public void onEnter()
    {
        homeView.setNotes(noteDao.getNotesSortedByDateAsc());
    }

    @Override
    public View getView()
    {
        return homeView;
    }
}
