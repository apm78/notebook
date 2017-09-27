package de.freigeistit.notebook.datasource.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author Axel P. Meier
 */
public class DateTimeConverterTest
{
    @Test
    public void testDateTimeConversion(){
        final Date testDate = new Date();
        Assert.assertEquals(testDate, DateTimeConverter.toDate(DateTimeConverter.toLocalDateTime(testDate)));
    }
}
