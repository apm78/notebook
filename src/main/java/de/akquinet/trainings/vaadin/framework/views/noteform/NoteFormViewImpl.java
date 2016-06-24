package de.akquinet.trainings.vaadin.framework.views.noteform;

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
import de.akquinet.trainings.vaadin.framework.backend.Note;
import de.akquinet.trainings.vaadin.framework.views.vaadin.DateToZonedDateTimeConverter;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NoteFormViewImpl implements NoteFormView
{
    private static final String PROP_TITLE = "title";
    private static final String PROP_DESCRIPTION = "description";
    private static final String PROP_TIME = "time";

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final FieldGroup fieldGroup = new FieldGroup();
    private Observer observer;

    public NoteFormViewImpl()
    {
        final FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");

        final TextField titleField = new TextField("Title");
        titleField.setWidth("100%");
        titleField.setRequired(true);
        titleField.setRequiredError("Title of note is mandatory.");
        final TextArea descriptionField = new TextArea("Desciption");
        descriptionField.setWidth("100%");
        descriptionField.setRows(6);
        final DateField dateField = new DateField("Date");
        dateField.setWidth("100%");
        dateField.setResolution(Resolution.MINUTE);
        dateField.setConverter(new DateToZonedDateTimeConverter());
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
                            Notification.show("Note couldn't be saved!");
                            e.printStackTrace();
                        }
                    }
                });
        saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        buttonLayout.addComponents(cancelButton, saveButton);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.BOTTOM_RIGHT);
        buttonLayout.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
        buttonLayout.setExpandRatio(cancelButton, 1f);

        final Label title = new Label("Edit Note");
        title.addStyleName(ValoTheme.LABEL_H2);
        rootLayout.addComponent(title);
        rootLayout.addComponent(formLayout);
        rootLayout.setWidth("600px");
        rootLayout.setHeight("450px");
        rootLayout.addComponent(buttonLayout);
        rootLayout.setExpandRatio(buttonLayout, 1f);
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
    public void setNote(final Note note)
    {
        fieldGroup.setItemDataSource(new BeanItem<>(note, Note.class));
    }

    @Override
    public Note getNote()
    {
        @SuppressWarnings("unchecked")
        final BeanItem<Note> beanItem = (BeanItem<Note>) fieldGroup.getItemDataSource();
        return beanItem.getBean();
    }
}
