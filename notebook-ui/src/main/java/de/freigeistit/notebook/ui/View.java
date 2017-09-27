package de.freigeistit.notebook.ui;

/**
 * @author Axel P. Meier
 */
public interface View
{
    <C> C getComponent(Class<C> type);
}
