package ru.netcracker.chinagram.repositories;

import ru.netcracker.chinagram.model.AbstractEntity;

import java.util.List;


public interface ChinaRepository {

    public <T extends AbstractEntity> List<T> findAll(Class<T> clazz);
}
