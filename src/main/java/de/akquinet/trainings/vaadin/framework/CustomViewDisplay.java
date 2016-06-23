package de.akquinet.trainings.vaadin.framework;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.SingleComponentContainer;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class CustomViewDisplay implements ViewDisplay
{
    public final SingleComponentContainer contentNode;

    public CustomViewDisplay(final SingleComponentContainer contentNode)
    {
        this.contentNode = contentNode;
    }

    @Override
    public void showView(final View view)
    {
        if (view instanceof de.akquinet.trainings.vaadin.framework.View)
        {
            final Component newContent = ((de.akquinet.trainings.vaadin.framework.View) view)
                    .getComponent(Component.class);
            contentNode.setContent(newContent);
        }
        else
        {
            throw new IllegalArgumentException("View is not a de.akquinet.trainings.vaadin.framework.View): "
                    + view);
        }
    }
}
