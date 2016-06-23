package de.akquinet.trainings.vaadin.framework.views.vaadin;

import com.vaadin.data.util.converter.Converter;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime>
{
    @Override
    public ZonedDateTime convertToModel(final Date date, final Class<? extends ZonedDateTime> aClass, final Locale locale) throws ConversionException
    {
        return ZonedDateTime.ofInstant(date.toInstant(), BrowserTimeZone.getBrowserTimeZoneId());
    }

    @Override
    public Date convertToPresentation(final ZonedDateTime zonedDateTime, final Class<? extends Date> aClass, final Locale locale) throws ConversionException
    {
        return Date.from(zonedDateTime.toInstant());
    }

    @Override
    public Class<ZonedDateTime> getModelType()
    {
        return ZonedDateTime.class;
    }

    @Override
    public Class<Date> getPresentationType()
    {
        return Date.class;
    }
}
