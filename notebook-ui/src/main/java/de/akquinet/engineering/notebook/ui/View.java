package de.akquinet.engineering.notebook.ui;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface View
{
    <C> C getComponent(Class<C> type);
}
