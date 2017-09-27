package de.freigeistit.notebook.ui.i18n;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.UI;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Axel P. Meier
 */
@UIScoped
public class I18nImpl implements I18n
{
    private static final String BASE_NAME = "messages";

    public I18nImpl()
    {
    }

    private ResourceBundle getBundle(final Locale locale){
        final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);
        return bundle != null ? bundle : ResourceBundle.getBundle(BASE_NAME, Locale.US);
    }

    private String getByLocale(final Locale locale, final String key){
        return getBundle(locale).getString(key);
    }

    private String getByLocale(final Locale locale, final String key, final Object... params){
        final MessageFormat messageFormat = new MessageFormat(getByLocale(locale, key), locale);
        return messageFormat.format(params);
    }

    private Locale getCurrentLocale(){
        return UI.getCurrent().getLocale();
    }

    @Override
    public String get(final String key)
    {
        return getByLocale(getCurrentLocale(), key);
    }

    @Override
    public String get(final String key, final Object... params)
    {
        return getByLocale(getCurrentLocale(), key, params);
    }
}
