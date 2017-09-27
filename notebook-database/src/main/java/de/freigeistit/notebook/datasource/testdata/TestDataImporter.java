package de.freigeistit.notebook.datasource.testdata;

import de.freigeistit.notebook.datasource.entity.Note;
import de.freigeistit.notebook.datasource.entity.Notebook;
import de.freigeistit.notebook.datasource.entity.Role;
import de.freigeistit.notebook.datasource.entity.User;
import de.freigeistit.notebook.datasource.util.DateTimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
@RunAs("admin") // set the current role to admin to enable the execution of protected methods
public class TestDataImporter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataImporter.class);

    private static final int NR_TEST_USER = 6;
    private static final int NR_TEST_ADMIN = 4;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void insertTestData()
    {
        final List<User> allUsers = findAllUsers();
        if (allUsers.isEmpty())
        {
            final Role userRole = new Role("user");
            em.persist(userRole);
            final Role adminRole = new Role("admin");
            em.persist(adminRole);

            LOGGER.info("Creating new test users");
            for (int i = 0; i <= NR_TEST_USER; i++)
            {
                User user = User.createUser("user" + i, "secret", "John_" + (NR_TEST_USER - i), "Doe_" + i);
                user.addRole(userRole);

                createAndPersistNotebookWithNotes(user);

                em.persist(user);
            }
            for (int i = 0; i <= NR_TEST_ADMIN; i++)
            {
                User user = User.createUser("admin" + i, "secret", "Adam_" + (NR_TEST_ADMIN - i), "Administrator_" + i);
                user.addRole(adminRole);
                user.addRole(userRole);

                createAndPersistNotebookWithNotes(user);

                em.persist(user);
            }
            LOGGER.info("Created " + (NR_TEST_USER + NR_TEST_ADMIN) + " test users.");
            em.flush();
        }
        else
        {
            LOGGER.info("There are users in the database. I will not create additional ones.");
        }
    }

    private void createAndPersistNotebookWithNotes(final User user)
    {
        final Notebook notebook = new Notebook(user);
        em.persist(notebook);

        for (final Note note : createNotes())
        {
            notebook.addNote(note);
            em.persist(note);
        }
    }

    private List<Note> createNotes()
    {
        final List<Note> notes = new ArrayList<>();
        final ZoneId zoneId = ZoneId.systemDefault();
        notes.add(new Note("Laundry", "Do the laundry.", DateTimeConverter.toDate(LocalDateTime.now(zoneId))));
        notes.add(new Note("Dishes", "Do the dishes.", DateTimeConverter.toDate(LocalDateTime.now(zoneId).plusDays(1))));
        notes.add(new Note("TV time", "Watch tv.", DateTimeConverter.toDate(LocalDateTime.now(zoneId).plusHours(4))));
        notes.add(new Note("Work", "Go to work.", DateTimeConverter.toDate(LocalDateTime.now(zoneId).minusHours(4))));
        return notes;
    }

    private List<User> findAllUsers()
    {
        LOGGER.debug("findAllUsers()");
        return em.createNamedQuery(User.FIND_ALL_USERS, User.class)
                .getResultList();
    }

}
