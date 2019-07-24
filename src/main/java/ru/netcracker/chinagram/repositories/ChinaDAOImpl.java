package ru.netcracker.chinagram.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netcracker.chinagram.model.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Repository
public class ChinaDAOImpl implements ChinaDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public <T extends AbstractEntity> List<T> findAll(Class<T> clazz) {
        return entityManager.createQuery("from " + clazz.getSimpleName() + " e").getResultList();
    }

    @Transactional
    public <T extends AbstractEntity> List<T> findAllByField(Class<T> clazz, String field, Object value) {
        Query query = entityManager.createQuery(String.format("from %s where %s = :%s", clazz.getSimpleName(), field, field));
        query.setParameter(field, value);
        return query.getResultList();
    }

    @Transactional
    public <T extends AbstractEntity> T get(Class<T> clazz, String field, Object value) {
        return findAllByField(clazz, field, value).get(0);
    }

    @Transactional
    public <T extends AbstractEntity> T get(Class<T> clazz, UUID id) {
        return get(clazz, "id", id);
    }

    @Transactional
    public <T extends AbstractEntity> void remove(T object) {
        entityManager.remove(object);
    }

    @Transactional
    public <T extends AbstractEntity> void merge(T object) {
        entityManager.merge(object);
    }

    @Transactional
    public <T extends AbstractEntity> void persist(T object) { entityManager.persist(object); }

}
