package ru.netcracker.chinagram.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.netcracker.chinagram.model.AbstractEntity;

import java.util.List;
import java.util.UUID;


public interface ChinaDAO {
    public <T extends AbstractEntity> List<T> findAll(Class<T> clazz);
    public <T extends AbstractEntity> List<T> findAllByField(Class<T> clazz, String field, Object value);
    public <T extends AbstractEntity> T get(Class<T> clazz, String field, Object value);
    public <T extends AbstractEntity> T get(Class<T> clazz, UUID id);
    public <T extends AbstractEntity> void remove(T object);
    public <T extends AbstractEntity> void merge(T object);
    public <T extends AbstractEntity> void persist(T object);
}
