/*
 * WEAT EABR
 */
package de.akquinet.engineering.notebook.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.server.VaadinCDIServlet;

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
}
