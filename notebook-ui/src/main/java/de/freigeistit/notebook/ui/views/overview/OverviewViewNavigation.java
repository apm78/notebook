package de.freigeistit.notebook.ui.views.overview;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import javax.inject.Inject;

/**
 * @author Axel P. Meier
 */
@CDIView(OverviewViewNavigation.VIEW_NAME)
public class OverviewViewNavigation implements View, de.freigeistit.notebook.ui.View
{
    public final static String VIEW_NAME = "overview";

    @Inject
    private OverviewPresenter presenter;

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {
        presenter.onEnter();
    }

    @Override
    public <C> C getComponent(final Class<C> type)
    {
        return presenter.getView().getComponent(type);
    }
}
