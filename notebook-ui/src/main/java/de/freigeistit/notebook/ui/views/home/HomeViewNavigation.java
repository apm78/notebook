package de.freigeistit.notebook.ui.views.home;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import javax.inject.Inject;

/**
 * @author Axel P. Meier
 */

@CDIView(HomeViewNavigation.VIEW_NAME)
public class HomeViewNavigation implements View, de.freigeistit.notebook.ui.View
{
    public final static String VIEW_NAME = "";

    @Inject
    private HomeView homeView;

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {
        homeView.onEnter();
    }

    @Override
    public <C> C getComponent(final Class<C> type)
    {
        return homeView.getComponent(type);
    }
}
