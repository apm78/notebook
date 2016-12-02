package de.akquinet.engineering.notebook.datasource.dao;

import de.akquinet.engineering.notebook.datasource.entity.User;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public interface UserDao
{
    User findUserByLogin(String login);
}
