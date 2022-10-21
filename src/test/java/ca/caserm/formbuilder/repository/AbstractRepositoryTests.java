package ca.caserm.formbuilder.repository;

import ca.caserm.formbuilder.AbstractTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.io.IOException;

public abstract class AbstractRepositoryTests extends AbstractTests {

    @Autowired
    private TestEntityManager entityManager;

    protected <E> E saveTestEntity(String fileSource, Class<E> valueType) throws IOException {
        E entity = getObjectFromJson(fileSource, valueType);
        saveTestEntity(entity);
        return entity;
    }

    protected <E> E saveTestEntity(E entity) {
        try {
            return entityManager.persist(entity);
        } finally {
            entityManager.flush();
        }
    }

}
