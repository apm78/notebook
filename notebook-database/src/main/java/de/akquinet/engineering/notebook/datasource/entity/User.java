package de.akquinet.engineering.notebook.datasource.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;

import static de.akquinet.engineering.notebook.datasource.entity.User.FIND_ALL_USERS;
import static de.akquinet.engineering.notebook.datasource.entity.User.FIND_USER_BY_ID;

@Entity
@Table(name = "cm_user")
@NamedQueries({
        @NamedQuery(name = FIND_ALL_USERS, query = "select user from User user order by user.login"),
        @NamedQuery(name = FIND_USER_BY_ID, query = "select user from User user where user.id = :id")
})
public class User extends AbstractEntity {

    public static final String FIND_ALL_USERS = "User.findAllUsers";
    public static final String FIND_USER_BY_ID = "User.findUserById";

    @Basic
    private String login;

    @Basic
    private String password;

    @Basic
    private String firstName;

    @Basic
    private String lastName;


    @ManyToMany(mappedBy = "users")
    private Collection<Role> roles = new HashSet<Role>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean checkPassword(String password) {
        if (password == null) {
            return false;
        } else {
            return password.equals(this.password);
        }
    }

    public void changePassword(String passwordOld, String passwordNew) {
        if (checkPassword(passwordOld)) {
            this.password = passwordNew;
        }
    }

    public static User createUser(String login, String password, String firstName, String lastName) {
        User user = new User();
        user.setLogin(login);
        user.password = password;
        user.firstName = firstName;
        user.lastName = lastName;
        return user;
    }

    public void addRole(Role role) {
        if (!(roles.contains(role))) {
            roles.add(role);
            role.addUser(this);
        }
    }

}
