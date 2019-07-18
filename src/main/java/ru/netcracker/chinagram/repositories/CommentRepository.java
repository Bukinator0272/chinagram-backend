package ru.netcracker.chinagram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netcracker.chinagram.model.Comment;
import ru.netcracker.chinagram.model.User;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
