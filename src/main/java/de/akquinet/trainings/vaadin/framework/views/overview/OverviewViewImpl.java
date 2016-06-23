package de.akquinet.trainings.vaadin.framework.views.overview;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;
import de.akquinet.trainings.vaadin.framework.backend.Note;
import de.akquinet.trainings.vaadin.framework.views.vaadin.DateToZonedDateTimeConverter;

import java.util.Collection;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class OverviewViewImpl implements OverviewView
{
    private final static String PROP_ID = "id";
    private final static String PROP_TITLE = "title";
    private final static String PROP_DESCRIPTION = "description";
    private final static String PROP_TIME = "time";

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final BeanItemContainer<Note> container = new BeanItemContainer<>(Note.class);

    public OverviewViewImpl()
    {
        final Grid grid = new Grid("Notes");
        grid.setContainerDataSource(container);
        grid.removeColumn(PROP_ID);
        grid.getDefaultHeaderRow().getCell(PROP_TITLE).setHtml("<b>Title</b>");
        grid.getColumn(PROP_DESCRIPTION).setHeaderCaption("Description");
        grid.getColumn(PROP_TIME).setHeaderCaption("Time");
        grid.getColumn(PROP_TIME).setRenderer(new DateRenderer(),
                new DateToZonedDateTimeConverter());
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(10d);
        grid.setWidth("100%");

        grid.setColumnOrder(PROP_TITLE, PROP_DESCRIPTION, PROP_TIME);

        rootLayout.setMargin(true);
        rootLayout.addComponent(grid);
    }

    @Override
    public <C> C getComponent(final Class<C> type)
    {
        return type.cast(rootLayout);
    }


    @Override
    public void setNotes(final Collection<Note> notes)
    {
        container.removeAllItems();
        container.addAll(notes);
        container.sort(new Object[]{PROP_TIME}, new boolean[]{true});
    }
}
