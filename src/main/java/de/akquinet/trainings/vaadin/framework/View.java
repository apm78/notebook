package de.akquinet.trainings.vaadin.framework;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface View
{
    <C> C getComponent(Class<C> type);
}
