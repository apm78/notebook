package de.akquinet.engineering.notebook.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.notebook.ui.i18n.I18n;
import de.akquinet.engineering.notebook.ui.views.home.HomeViewNavigation;
import de.akquinet.engineering.notebook.ui.views.overview.OverviewViewNavigation;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@CDIUI("")
@Theme("notebooktheme")
public class NotebookUI extends UI
{
    private static final String SESSION_LOCALE = "sessionLocale";

    @Inject
    private CDIViewProvider viewProvider;

    @Inject
    private I18n i18n;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        getLocaleFromSession();

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

    private void getLocaleFromSession()
    {
        final Locale sessionLocale = (Locale) VaadinSession.getCurrent().getSession().getAttribute(SESSION_LOCALE);
        if (sessionLocale != null)
        {
            setLocale(sessionLocale);
        }
    }

    private void setLocaleToSession(final Locale locale){
        VaadinSession.getCurrent().getSession().setAttribute(SESSION_LOCALE, locale);
    }

    private Component createNavigation(){
        final CssLayout navigationLayout = new CssLayout();
        navigationLayout.setPrimaryStyleName(ValoTheme.MENU_ROOT);
        final CssLayout navigationBody = new CssLayout();
        navigationBody.addStyleName(ValoTheme.MENU_PART);
        final CssLayout navigationTitle = new CssLayout();
        navigationTitle.addStyleName(ValoTheme.MENU_TITLE);
        final Label title = new Label(i18n.get("application.name"));
        navigationTitle.addComponent(title);
        navigationBody.addComponent(navigationTitle);

        final Label logo = new Label(FontAwesome.PENCIL.getHtml(), ContentMode.HTML);
        logo.setSizeUndefined();
        logo.setPrimaryStyleName(ValoTheme.MENU_LOGO);
        navigationBody.addComponent(logo);

        // display user info
        final CssLayout userLayout = new CssLayout();
        final String userName = JaasAccessControl.getCurrentRequest().getUserPrincipal().getName();
        final Label userInfo = new Label();
        userInfo.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        userInfo.setValue(userName);

        final NativeSelect langSelect = new NativeSelect();
        langSelect.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        langSelect.setNullSelectionAllowed(false);
        langSelect.setContainerDataSource(new BeanItemContainer<>(Locale.class, Arrays.asList(Locale.US, Locale.GERMANY)));
        for (final Object itemId : langSelect.getContainerDataSource().getItemIds()){
            final Locale locale = (Locale) itemId;
            langSelect.setItemCaption(itemId, locale.getDisplayLanguage(locale));
        }
        langSelect.setValue(Locale.GERMANY.equals(getLocale()) ? getLocale() : Locale.US);
        langSelect.addValueChangeListener(e -> {
            setLocaleToSession((Locale) e.getProperty().getValue());
            // refresh UI
            getPage().reload();
        });

        final Label divider = new Label();
        divider.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
        userLayout.addComponents(userInfo, langSelect, divider);
        navigationBody.addComponent(userLayout);

        navigationBody.addStyleName(ValoTheme.MENU_PART);
        final Button homeButton = new Button(i18n.get("menu.home"), FontAwesome.HOME);
        homeButton.addClickListener(e -> getNavigator().navigateTo(HomeViewNavigation.VIEW_NAME));
        homeButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        final Button dataButton = new Button(i18n.get("menu.overview"), FontAwesome.TABLE);
        dataButton.addClickListener(e -> getNavigator().navigateTo(OverviewViewNavigation.VIEW_NAME));
        dataButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);

        final Button logoutButton = new Button(i18n.get("menu.logout"), FontAwesome.SIGN_OUT);
        logoutButton.addClickListener(e -> logout());
        logoutButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);

        navigationBody.addComponents(homeButton, dataButton, logoutButton);
        navigationLayout.addComponents(navigationBody);
        return navigationLayout;
    }

    private static void logout(){
        VaadinSession.getCurrent().getSession().invalidate();
        Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletContext().getContextPath());
    }
}
