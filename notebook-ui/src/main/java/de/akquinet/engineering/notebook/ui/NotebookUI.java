package de.akquinet.engineering.notebook.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.notebook.ui.views.home.HomeViewNavigation;
import de.akquinet.engineering.notebook.ui.views.overview.OverviewViewNavigation;

import javax.inject.Inject;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@CDIUI("")
@Theme("notebooktheme")
@PreserveOnRefresh
public class NotebookUI extends UI
{
    @Inject
    private CDIViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        final Panel contentPanel = new Panel();
        contentPanel.setSizeFull();

        layout.addComponents(createNavigation(), contentPanel);
        layout.setExpandRatio(contentPanel, 1f);

        setContent(layout);

        setNavigator(new Navigator(this, new CustomViewDisplay(contentPanel)));
        getNavigator().addProvider(viewProvider);
    }

    private Component createNavigation(){
        final CssLayout navigationLayout = new CssLayout();
        navigationLayout.setPrimaryStyleName(ValoTheme.MENU_ROOT);
        final CssLayout navigationBody = new CssLayout();
        navigationBody.addStyleName(ValoTheme.MENU_PART);
        final CssLayout navigationTitle = new CssLayout();
        navigationTitle.addStyleName(ValoTheme.MENU_TITLE);
        final Label title = new Label("Notebook App");
        navigationTitle.addComponent(title);
        navigationBody.addComponent(navigationTitle);

        final Label logo = new Label(FontAwesome.PENCIL.getHtml(), ContentMode.HTML);
        logo.setSizeUndefined();
        logo.setPrimaryStyleName(ValoTheme.MENU_LOGO);
        navigationBody.addComponent(logo);

        // display user info
        final String userName = JaasAccessControl.getCurrentRequest().getUserPrincipal().getName();
        final Label userInfo = new Label();
        userInfo.setValue(userName);
        userInfo.addStyleName(ValoTheme.MENU_SUBTITLE);
        navigationBody.addComponent(new CssLayout(userInfo));

        navigationBody.addStyleName(ValoTheme.MENU_PART);
        final Button homeButton = new Button("Home", FontAwesome.HOME);
        homeButton.addClickListener(e -> getNavigator().navigateTo(HomeViewNavigation.VIEW_NAME));
        homeButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        final Button dataButton = new Button("Overview", FontAwesome.TABLE);
        dataButton.addClickListener(e -> getNavigator().navigateTo(OverviewViewNavigation.VIEW_NAME));
        dataButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);

        final Button logoutButton = new Button("Logout", FontAwesome.SIGN_OUT);
        logoutButton.addClickListener(e -> {
            VaadinSession.getCurrent().getSession().invalidate();
            Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletContext().getContextPath());
//            JaasAccessControl.logout();
        });
        logoutButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);

        navigationBody.addComponents(homeButton, dataButton, logoutButton);
        navigationLayout.addComponents(navigationBody);
        return navigationLayout;
    }


}