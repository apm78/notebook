package de.akquinet.engineering.notebook.ui.auth;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NotebookLoginModule implements LoginModule
{
    private CallbackHandler callbackHandler;
    private Subject subject;
    private UserPrincipal userPrincipal;
    private RolePrincipal rolePrincipal;
    private String login;
    private List<String> userGroups;

    @Override
    public void initialize(final Subject subject, final CallbackHandler callbackHandler,
            final Map<String, ?> sharedState, final Map<String, ?> options)
    {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean abort() throws LoginException
    {
        return false;
    }

    @Override
    public boolean commit() throws LoginException
    {
        userPrincipal = new UserPrincipal(login);
        subject.getPrincipals().add(userPrincipal);

        if (userGroups != null && userGroups.size() > 0) {
            for (String groupName : userGroups) {
                rolePrincipal = new RolePrincipal(groupName);
                subject.getPrincipals().add(rolePrincipal);
            }
        }

        return true;
    }

    @Override
    public boolean login() throws LoginException
    {
        final NameCallback nameCallback = new NameCallback("username");
        final PasswordCallback passwordCallback = new PasswordCallback("password", false);
        final Callback[] callbacks = new Callback[] { nameCallback, passwordCallback };

        try
        {
            callbackHandler.handle(callbacks);
            final String name = nameCallback.getName();
            final String password = String.valueOf(passwordCallback.getPassword());

            if ("admin".equals(name)
                    && "123".equals(password))
            {
                login = name;
                userGroups = Arrays.asList("admin");
                return true;
            }

            throw new LoginException("Authentication failed");

        }
        catch (IOException e)
        {
            throw new LoginException(e.getMessage());
        }
        catch (UnsupportedCallbackException e)
        {
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public boolean logout() throws LoginException
    {
        subject.getPrincipals().remove(userPrincipal);
        subject.getPrincipals().remove(rolePrincipal);
        return true;
    }
}
