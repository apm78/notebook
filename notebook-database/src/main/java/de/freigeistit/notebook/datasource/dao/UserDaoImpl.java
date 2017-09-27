package de.freigeistit.notebook.datasource.dao;

import de.freigeistit.notebook.datasource.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Axel P. Meier
 */
@Stateless
public class UserDaoImpl implements UserDao
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUserByLogin(final String login)
    {
        try
        {
            return entityManager.createNamedQuery(User.FIND_USER_BY_LOGIN, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        }
        catch (final NoResultException e)
        {
            return null;
        }
    }
}
