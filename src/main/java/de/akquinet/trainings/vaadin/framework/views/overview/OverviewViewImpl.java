package de.akquinet.trainings.vaadin.framework.views.overview;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.trainings.vaadin.framework.View;
import de.akquinet.trainings.vaadin.framework.backend.Note;
import de.akquinet.trainings.vaadin.framework.views.vaadin.ConfirmationDialog;
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
    private final static String PROP_DELETE = "delete";

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final BeanItemContainer<Note> container = new BeanItemContainer<>(Note.class);
    private final Panel editorPanel = new Panel();
    private Observer observer;
    private final Grid grid;

    public OverviewViewImpl()
    {
        grid = new Grid();
        final GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(container);
        grid.setContainerDataSource(gpc);
        gpc.addGeneratedProperty(PROP_DELETE, new PropertyValueGenerator<String>()
        {
            @Override
            public Class<String> getType()
            {
                return String.class;
            }

            @Override
            public String getValue(final Item item, final Object itemId, final Object propertyId)
            {
                return "Delete";
            }
        });
        grid.removeColumn(PROP_ID);
        grid.getDefaultHeaderRow().getCell(PROP_TITLE).setStyleName(ValoTheme.LABEL_BOLD);
        grid.getColumn(PROP_TITLE).setHeaderCaption("Title");
        grid.getColumn(PROP_DESCRIPTION).setHeaderCaption("Description");
        grid.getColumn(PROP_TIME).setHeaderCaption("Time");
        grid.getColumn(PROP_TIME).setRenderer(new DateRenderer(),
                new DateToZonedDateTimeConverter());
        grid.getColumn(PROP_DELETE).setRenderer(new ButtonRenderer((ClickableRenderer.RendererClickListener) event -> {
            final Note selectedNote = (Note) event.getItemId();
            if (selectedNote != null)
            {
                final ConfirmationDialog confirmDlg = new ConfirmationDialog();
                confirmDlg.getWindow().setCaption("Delete Note");
                confirmDlg.getDescription().setValue("Do you really want to delete this note?");
                confirmDlg.getOkButton().addClickListener(e -> {
                    if (observer != null)
                    {
                        observer.onDelete(selectedNote);
                    }
                });
                confirmDlg.show();
            }
        }));

        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(8d);
        grid.setWidth("100%");

        grid.setColumnOrder(PROP_TITLE, PROP_DESCRIPTION, PROP_TIME);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener((SelectionEvent.SelectionListener) event -> {
            if (observer != null)
            {
                final Set<Object> selected = event.getSelected();
                if (selected.size() == 1)
                {
                    final Note selectedNote = (Note) selected.iterator().next();
                    observer.onSelect(selectedNote);
                }
                else
                {
                    observer.onSelect(null);
                }
            }
        });
        final Grid.FooterRow footer = grid.appendFooterRow();
        final Button addButton = new Button("Add Note");
        addButton.addClickListener((Button.ClickListener) event -> {
            if (observer != null)
            {
                observer.onAdd();
            }
        });
        footer.getCell(PROP_DELETE).setComponent(addButton);

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
    public void selectNote(final Note note)
    {
        grid.select(note);
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
