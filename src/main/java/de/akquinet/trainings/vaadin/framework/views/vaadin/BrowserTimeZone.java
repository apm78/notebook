package de.akquinet.trainings.vaadin.framework.views.vaadin;

import com.vaadin.server.Page;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class BrowserTimeZone
{
    public static ZoneId getBrowserTimeZoneId(){
        final String[] timeZoneIds = TimeZone.getAvailableIDs(Page.getCurrent().getWebBrowser().getRawTimezoneOffset());
        for (final String id : timeZoneIds)
        {
            final ZoneId zoneId = TimeZone.getTimeZone(id).toZoneId();
            return zoneId;
        }
        return ZoneId.systemDefault();
    }
}
