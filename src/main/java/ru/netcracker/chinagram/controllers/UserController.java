package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.exceptions.UserNotFoundException;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.ChinaDAO;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    ChinaDAO chinaDAO;

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) throws UserNotFoundException {
        User user = chinaDAO.get(User.class, UUID.fromString(id));
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/usernames/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username) {
        User user = chinaDAO.get(User.class, "username", username);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        chinaDAO.persist(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/remove/{id}")
    public ResponseEntity<User> removeUserById(@PathVariable String id) { //заработало
        User user = chinaDAO.get(User.class, UUID.fromString(id));
        chinaDAO.remove(user);
        return new ResponseEntity<User>(HttpStatus.GONE);
    }


    @PutMapping("/follow/{followerId}/{followingId}")
    public ResponseEntity<List<User>> followUser(@PathVariable String followerId, @PathVariable String followingId) {
        User followerUser = chinaDAO.get(User.class, UUID.fromString(followerId));
        User followingUser = chinaDAO.get(User.class, UUID.fromString(followingId));
        followerUser.getFollowing().add(followingUser);
        chinaDAO.merge(followerUser);

        ResponseEntity<List<User>> responseEntity =
                new ResponseEntity<List<User>>(followerUser.getFollowing(), HttpStatus.OK);

        return responseEntity;
    }

    @PutMapping("/unfollow/{followerId}/{followingId}")
    public ResponseEntity<List<User>> unfollowUser(@PathVariable String followerId, @PathVariable String followingId) {
        User followerUser = chinaDAO.get(User.class, UUID.fromString(followerId));
        User followingUser = followerUser.getFollowing().stream().filter(e -> e.getId().toString()
                .equals(followingId)).findFirst().get();

        followerUser.getFollowing().remove(followingUser);
        chinaDAO.merge(followerUser);

        ResponseEntity<List<User>> responseEntity =
                new ResponseEntity<List<User>>(followerUser.getFollowing(), HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping("/users/followers/{userId}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowers(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/followings/{userId}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowing(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/followers_amount/{userId}")
    public ResponseEntity<Integer> getAmountOFFollowers(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowers().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/followings_amount/{userId}")
    public ResponseEntity<Integer> getAmountOfFollowings(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowing().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
