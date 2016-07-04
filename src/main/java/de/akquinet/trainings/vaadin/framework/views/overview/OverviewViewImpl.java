package de.akquinet.trainings.vaadin.framework.views.overview;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;
import de.akquinet.trainings.vaadin.framework.views.vaadin.DateToZonedDateTimeConverter;

import java.util.Collection;
import java.util.Set;

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
    private final Panel editorPanel = new Panel();
    private Observer observer;

    public OverviewViewImpl()
    {
        final Grid grid = new Grid();
        grid.setContainerDataSource(container);
        grid.removeColumn(PROP_ID);
        grid.getDefaultHeaderRow().getCell(PROP_TITLE).setStyleName(ValoTheme.LABEL_BOLD);
        grid.getColumn(PROP_TITLE).setHeaderCaption("Title");
        grid.getColumn(PROP_DESCRIPTION).setHeaderCaption("Description");
        grid.getColumn(PROP_TIME).setHeaderCaption("Time");
        grid.getColumn(PROP_TIME).setRenderer(new DateRenderer(),
                new DateToZonedDateTimeConverter());
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(8d);
        grid.setWidth("100%");

        grid.setColumnOrder(PROP_TITLE, PROP_DESCRIPTION, PROP_TIME);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener((SelectionEvent.SelectionListener) event -> {
            if (observer != null){
                final Set<Object> selected = event.getSelected();
                if (selected.size() == 1)
                {
                    final Note selectedNote = (Note) selected.iterator().next();
                    observer.onSelect(selectedNote);
                }
                else{
                    observer.onSelect(null);
                }
            }
        });

        final Label header = new Label("All Notes");
        header.addStyleName(ValoTheme.LABEL_H2);
        rootLayout.setMargin(true);
        rootLayout.setSpacing(true);
        rootLayout.addComponent(header);

        rootLayout.addComponent(grid);
        rootLayout.addComponent(editorPanel);
        editorPanel.setSizeUndefined();
    }

    @Override
    public void setObserver(final Observer observer)
    {
        this.observer = observer;
    }

    @Override
    public void setEditorView(final View view)
    {
        final VerticalLayout frame = new VerticalLayout();
        frame.setMargin(true);
        frame.addComponent(view.getComponent(Component.class));
        editorPanel.setContent(frame);
    }

    @Override
    public void setEditorVisible(final boolean visible)
    {
        editorPanel.setVisible(visible);
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
