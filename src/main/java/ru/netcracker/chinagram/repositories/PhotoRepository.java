package ru.netcracker.chinagram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {

}
