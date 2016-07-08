/*
 * WEAT EABR
 */
package de.akquinet.engineering.notebook.ui.i18n;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.UI;

import javax.inject.Inject;
import java.util.Locale;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
@UIScoped
public class I18nImpl implements I18n
{
    @Inject
    private MessageBundles bundles;

    public I18nImpl()
    {
    }

    private Locale getCurrentLocale(){
        return UI.getCurrent().getLocale();
    }

    @Override
    public String get(final String key)
    {
        return bundles.get(getCurrentLocale(), key);
    }

    @Override
    public String get(final String key, final Object... params)
    {
        return bundles.get(getCurrentLocale(), true, key, params);
    }

    @Override
    public String get(final boolean formatParams, final String key, final Object... params)
    {
        return bundles.get(getCurrentLocale(), key, params);
    }
}
