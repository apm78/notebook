package de.akquinet.engineering.notebook.ui.i18n;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface I18n
{
    String get(String key);

    String get(String key, Object... params);
}
