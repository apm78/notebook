package de.freigeistit.notebook.datasource.dao;

import de.freigeistit.notebook.datasource.entity.User;

/**
 * @author Axel P. Meier
 */
public interface UserDao
{
    User findUserByLogin(String login);
}
