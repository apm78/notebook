package de.akquinet.engineering.notebook.ui.views.vaadin;

import com.vaadin.data.util.converter.Converter;
import de.akquinet.engineering.notebook.datasource.util.DateTimeConverter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime>
{
    @Override
    public LocalDateTime convertToModel(final Date date, final Class<? extends LocalDateTime> aClass, final Locale locale) throws ConversionException
    {
        if (date == null){
            return null;
        }
        return DateTimeConverter.toLocalDateTime(date);
    }

    @Override
    public Date convertToPresentation(final LocalDateTime localDateTime, final Class<? extends Date> aClass, final Locale locale) throws ConversionException
    {
        if (localDateTime == null){
            return null;
        }
        return DateTimeConverter.toDate(localDateTime);
    }

    @Override
    public Class<LocalDateTime> getModelType()
    {
        return LocalDateTime.class;
    }

    @Override
    public Class<Date> getPresentationType()
    {
        return Date.class;
    }
}
