package ru.netcracker.chinagram.services.interfaces;

import ru.netcracker.chinagram.model.AbstractEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ChinaDAO {
    <T extends AbstractEntity> List<T> findAll(Class<T> clazz);
    <T extends AbstractEntity> List<T> findAll(Class<T> clazz, Integer from, Integer max);
    <T extends AbstractEntity> List<T> findAllByFieldLike(Class<T> clazz, String field, Object value);
    <T extends AbstractEntity> List<T> findAllByField(Class<T> clazz, String field, Object value);
    <T extends AbstractEntity> T get(Class<T> clazz, String field, Object value);
    <T extends AbstractEntity> T get(Class<T> clazz, UUID id);
    <T extends AbstractEntity> void remove(T object);
    <T extends AbstractEntity> void merge(T object);
    <T extends AbstractEntity> void persist(T object);
//    <T> List<T> executeQuery(String query,
//                             Map<String, Object> params,
//                             Integer maxResults,
//                             Integer firstResult);
}
