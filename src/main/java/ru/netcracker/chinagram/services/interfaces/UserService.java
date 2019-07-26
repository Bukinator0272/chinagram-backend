package ru.netcracker.chinagram.services.interfaces;

import org.springframework.stereotype.Service;
import ru.netcracker.chinagram.model.User;

import java.util.Optional;

@Service
public interface UserService {

    public boolean isValidUser(User user);

    public Optional<User> getFollowingUser(User user, String followingId);
}
