package ru.netcracker.chinagram.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netcracker.chinagram.model.User;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private ChinaDAO chinaDAO;

    public boolean isValidUser(User user) {
        return user != null && chinaDAO.get(User.class, user.getId()) == null
                && chinaDAO.get(User.class, "username", user.getUsername()) == null;

    }

    public Optional<User> getFollowingUser(User user, String followingId) {
       return user.getFollowing().stream().filter(e -> e.getId().toString()
                .equals(followingId)).findFirst();

    }
}
