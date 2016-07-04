package de.akquinet.trainings.vaadin.framework.views.vaadin;

import com.vaadin.data.util.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public Date convertToPresentation(final LocalDateTime localDateTime, final Class<? extends Date> aClass, final Locale locale) throws ConversionException
    {
        if (localDateTime == null){
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
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
