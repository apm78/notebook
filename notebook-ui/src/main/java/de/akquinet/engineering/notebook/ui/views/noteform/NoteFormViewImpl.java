package de.akquinet.engineering.notebook.ui.views.noteform;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.notebook.datasource.dto.NoteDto;
import de.akquinet.engineering.notebook.ui.views.vaadin.DateToLocalDateTimeConverter;
import de.akquinet.engineering.notebook.ui.views.vaadin.LazyValidationFieldGroup;

import javax.annotation.PostConstruct;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NoteFormViewImpl implements NoteFormView
{
    private static final String PROP_TITLE = "title";
    private static final String PROP_DESCRIPTION = "description";
    private static final String PROP_TIME = "time";

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final FieldGroup fieldGroup = new LazyValidationFieldGroup();
    private Observer observer;
    private final Label title = new Label();

    public NoteFormViewImpl()
    {

    }

    @PostConstruct
    public void init(){
        final FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");

        final TextField titleField = new TextField("Title");
        titleField.setWidth("100%");
        titleField.setNullRepresentation("");
        titleField.setRequired(true);
        titleField.setRequiredError("Title of note is mandatory.");
        final TextArea descriptionField = new TextArea("Description");
        descriptionField.setNullRepresentation("");
        descriptionField.setWidth("100%");
        descriptionField.setRows(6);
        final DateField dateField = new DateField("Date");
        dateField.setWidth("100%");
        dateField.setResolution(Resolution.MINUTE);
        dateField.setConverter(new DateToLocalDateTimeConverter());
        dateField.setRequired(true);
        dateField.setRequiredError("Date of note is mandatory.");

        fieldGroup.bind(titleField, PROP_TITLE);
        fieldGroup.bind(descriptionField, PROP_DESCRIPTION);
        fieldGroup.bind(dateField, PROP_TIME);
        formLayout.addComponents(titleField, descriptionField, dateField);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        buttonLayout.setSpacing(true);
        final Button cancelButton = new Button("Cancel",
                e ->
                {
                    if (observer != null)
                    {
                        fieldGroup.discard();
                        observer.onCancel();
                    }
                });

        final Button saveButton = new Button("Save",
                event ->
                {
                    if (observer != null)
                    {
                        try
                        {
                            fieldGroup.commit();
                            observer.onSave();
                        }
                        catch (FieldGroup.CommitException e)
                        {
                            Notification.show("Note couldn't be saved!", Notification.Type.WARNING_MESSAGE);
                            e.printStackTrace();
                        }
                    }
                });
        saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        buttonLayout.addComponents(cancelButton, saveButton);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.BOTTOM_RIGHT);
        buttonLayout.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
        buttonLayout.setExpandRatio(cancelButton, 1f);

        title.addStyleName(ValoTheme.LABEL_BOLD);
        rootLayout.addComponent(title);
        rootLayout.addComponent(formLayout);
        rootLayout.setWidth("600px");
        rootLayout.addComponent(buttonLayout);
    }

    @Override
    public <C> C getComponent(final Class<C> type)
    {
        return type.cast(rootLayout);
    }

    @Override
    public void setObserver(final Observer observer)
    {
        this.observer = observer;
    }

    @Override
    public void setNote(final NoteDto note)
    {
        fieldGroup.setItemDataSource(new BeanItem<>(note, NoteDto.class));
        title.setValue(note.getId() == null ? "New Note" : "Edit Note");
    }

    @Override
    public NoteDto getNote()
    {
        @SuppressWarnings("unchecked")
        final BeanItem<NoteDto> beanItem = (BeanItem<NoteDto>) fieldGroup.getItemDataSource();
        return beanItem.getBean();
    }
}
