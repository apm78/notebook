package de.freigeistit.notebook.ui.views.noteform;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.freigeistit.notebook.ui.i18n.I18n;
import de.freigeistit.notebook.ui.model.NoteDto;
import de.freigeistit.notebook.ui.views.vaadin.DateToLocalDateTimeConverter;
import de.freigeistit.notebook.ui.views.vaadin.LazyValidationFieldGroup;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Axel P. Meier
 */
public class NoteForm
{
    public interface Observer
    {
        void onSave();

        void onCancel();
    }

    private static final String PROP_TITLE = "title";
    private static final String PROP_DESCRIPTION = "description";
    private static final String PROP_TIME = "time";

    private final I18n i18n;

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final FieldGroup fieldGroup = new LazyValidationFieldGroup();
    private Observer observer;
    private final Label title = new Label();

    public NoteForm(final I18n i18n)
    {
        this.i18n = i18n;
        init();
    }

    private void init(){
        final FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");

        final TextField titleField = new TextField(i18n.get("note.form.title"));
        titleField.setWidth("100%");
        titleField.setNullRepresentation("");
        titleField.setRequired(true);
        titleField.setRequiredError(i18n.get("note.form.title.requiredError"));
        final TextArea descriptionField = new TextArea(i18n.get("note.form.description"));
        descriptionField.setNullRepresentation("");
        descriptionField.setWidth("100%");
        descriptionField.setRows(6);
        final DateField dateField = new DateField(i18n.get("note.form.date"));
        dateField.setWidth("100%");
        dateField.setResolution(Resolution.MINUTE);
        dateField.setConverter(new DateToLocalDateTimeConverter());
        dateField.setRequired(true);
        dateField.setRequiredError(i18n.get("note.form.description.requiredError"));

        fieldGroup.bind(titleField, PROP_TITLE);
        fieldGroup.bind(descriptionField, PROP_DESCRIPTION);
        fieldGroup.bind(dateField, PROP_TIME);
        formLayout.addComponents(titleField, descriptionField, dateField);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        buttonLayout.setSpacing(true);
        final Button cancelButton = new Button(i18n.get("form.buttonCancel.caption"),
                e ->
                {
                    if (observer != null)
                    {
                        fieldGroup.discard();
                        observer.onCancel();
                    }
                });

        final Button saveButton = new Button(i18n.get("form.buttonSave.caption"),
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
                            Notification.show(i18n.get("note.form.saveError"), Notification.Type.WARNING_MESSAGE);
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

    public void setNote(final NoteDto note)
    {
        fieldGroup.setItemDataSource(new BeanItem<>(note, NoteDto.class));
        title.setValue(i18n.get(note.getId() == null ? "note.form.newNote" : "note.form.editNote"));
    }

    public Component getRootLayout(){
        return rootLayout;
    }

    public NoteDto getNote()
    {
        @SuppressWarnings("unchecked")
        final BeanItem<NoteDto> beanItem = (BeanItem<NoteDto>) fieldGroup.getItemDataSource();
        return beanItem.getBean();
    }

    public void setObserver(final Observer observer){
        this.observer = observer;
    }
}
