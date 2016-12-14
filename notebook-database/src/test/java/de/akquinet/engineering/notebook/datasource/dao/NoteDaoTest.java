package de.akquinet.engineering.notebook.datasource.dao;

import de.akquinet.engineering.notebook.datasource.entity.Notebook;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class NoteDaoTest
{
    @Mock
    private EntityManager entityManager;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private NoteDaoImpl noteDao;

    private final Notebook notebook = new Notebook();

    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);

        // mock entity manager
        final TypedQuery queryMock = Mockito.mock(TypedQuery.class);
        Mockito.when(queryMock.setParameter(anyString(), anyObject()))
                .thenReturn(queryMock);
        Mockito.when(queryMock.getSingleResult())
                .thenReturn(notebook);
        Mockito.when(entityManager.createNamedQuery(anyString(),
                any(Class.class)))
                .thenReturn(queryMock);
    }
}
