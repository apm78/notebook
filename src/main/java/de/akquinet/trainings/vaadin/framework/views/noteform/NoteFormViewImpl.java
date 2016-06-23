package de.akquinet.trainings.vaadin.framework.views.noteform;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
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

        final TextField titleField = new TextField("Title");
        final TextArea descriptionField = new TextArea("Desciption");
        final DateField dateField = new DateField("Date");
        dateField.setResolution(Resolution.MINUTE);
        dateField.setConverter(new DateToZonedDateTimeConverter());
        
        fieldGroup.bind(titleField, PROP_TITLE);
        fieldGroup.bind(descriptionField, PROP_DESCRIPTION);
        fieldGroup.bind(dateField, PROP_TIME);
        formLayout.addComponents(titleField, descriptionField, dateField);

        rootLayout.addComponent(new Label("Edit Note"));
        rootLayout.addComponent(formLayout);
        rootLayout.setSpacing(true);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        final Button cancelButton = new Button("Cancel",
                e -> {
                    if (observer != null){
                        observer.onCancel();
                    }
                }) ;

        final Button saveButton = new Button("Save",
                e -> {
                    if (observer != null){
                        observer.onSave();
                    }
                }) ;

        buttonLayout.addComponents(cancelButton, saveButton);
        buttonLayout.setExpandRatio(cancelButton, 1f);
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
