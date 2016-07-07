package de.akquinet.engineering.notebook.ui.views.vaadin;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class LazyValidationFieldGroup  extends FieldGroup
{
    public LazyValidationFieldGroup()
    {
    }

    public LazyValidationFieldGroup(final Item itemDataSource)
    {
        super(itemDataSource);
    }

    @Override
    public void bind(final Field<?> field, final Object propertyId) throws BindException
    {
        if (field instanceof AbstractField){
            ((AbstractField)field).setValidationVisible(false);
        }
        super.bind(field, propertyId);
    }

    @Override
    public void commit() throws CommitException
    {
        for (final Field field : getFields())
        {
            if (field instanceof AbstractField){
                ((AbstractField) field).setValidationVisible(true);
            }
        }
        super.commit();
    }
}
