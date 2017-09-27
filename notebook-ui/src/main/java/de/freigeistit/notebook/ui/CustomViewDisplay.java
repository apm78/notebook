package de.freigeistit.notebook.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.SingleComponentContainer;

/**
 * @author Axel P. Meier
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
        if (view instanceof de.freigeistit.notebook.ui.View)
        {
            final Component newContent = ((de.freigeistit.notebook.ui.View) view)
                    .getComponent(Component.class);
            contentNode.setContent(newContent);
        }
        else
        {
            throw new IllegalArgumentException("View is not a View): "
                    + view);
        }
    }
}
