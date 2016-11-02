package de.akquinet.engineering.notebook.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@WebServlet(urlPatterns = { "/app/*", "/VAADIN/*" },
        name = "NotebookUIServlet",
        asyncSupported = true)
@VaadinServletConfiguration(ui = NotebookUI.class, productionMode = false)
public class NotebookUIServlet extends VaadinCDIServlet
{
    public String exitUrl(){
        return getServletContext().getContextPath();
    }

    protected void servletInitialized() throws ServletException
    {
        super.servletInitialized();

        getService().setSystemMessagesProvider(new SystemMessagesProvider()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo)
            {
                final CustomizedSystemMessages c = new CustomizedSystemMessages();
                final String exitUrl = exitUrl();
                c.setSessionExpiredURL(exitUrl);
                c.setSessionExpiredNotificationEnabled(false);

                c.setAuthenticationErrorURL(exitUrl);
                c.setAuthenticationErrorNotificationEnabled(false);

                c.setCommunicationErrorURL(exitUrl);
                c.setCommunicationErrorNotificationEnabled(false);

                c.setCookiesDisabledURL(exitUrl);
                c.setCookiesDisabledNotificationEnabled(false);

                c.setInternalErrorURL(exitUrl);
                c.setInternalErrorNotificationEnabled(false);

                c.setSessionExpiredURL(exitUrl);
                c.setSessionExpiredNotificationEnabled(false);
                return c;
            }
        });
    }
}
