package ru.netcracker.chinagram.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netcracker.chinagram.model.AbstractEntity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class ChinaRepositoryImpl implements ChinaRepository {

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Transactional
    public <T extends AbstractEntity> List<T> findAll(Class<T> clazz) {
        return emf.createEntityManager().createQuery("from " + clazz.getSimpleName() + " e").getResultList();
    }
}
