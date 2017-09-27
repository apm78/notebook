package de.freigeistit.notebook.ui.i18n;

/**
 * @author Axel P. Meier
 */
public interface I18n
{
    String get(String key);

    String get(String key, Object... params);
}
