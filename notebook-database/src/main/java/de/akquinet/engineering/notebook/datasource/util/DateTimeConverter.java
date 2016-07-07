package de.akquinet.engineering.notebook.datasource.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class DateTimeConverter
{
    public static Date toDate(final LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(final Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
