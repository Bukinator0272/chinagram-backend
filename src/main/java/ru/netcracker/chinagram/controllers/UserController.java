package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.services.interfaces.ChinaDAO;
import ru.netcracker.chinagram.services.interfaces.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private ChinaDAO chinaDAO;

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.isValidUser(user)) {
            chinaDAO.persist(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (userService.isValidUser(user)) {
            chinaDAO.merge(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/users/by_username/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username) {
        User user = chinaDAO.get(User.class, "username", username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity removeUserById(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            chinaDAO.remove(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/follow/{followerId}/{followingId}")
    public ResponseEntity<List> followUser(@PathVariable String followerId, @PathVariable String followingId) {
        User followerUser = chinaDAO.get(User.class, UUID.fromString(followerId));
        User followingUser = chinaDAO.get(User.class, UUID.fromString(followingId));
        if (followerUser != null && followingUser != null
                && !userService.getFollowingUser(followerUser, followingId).isPresent()) {
            followerUser.getFollowing().add(followingUser);
            chinaDAO.merge(followerUser);
            ResponseEntity<List> responseEntity =
                    new ResponseEntity(followerUser.getFollowing(), HttpStatus.OK);
            return responseEntity;

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/unfollow/{followerId}/{followingId}")
    public ResponseEntity<List<User>> unfollowUser(@PathVariable String followerId, @PathVariable String followingId) {
        User followerUser = chinaDAO.get(User.class, UUID.fromString(followerId));
        if (followerUser != null) {
            Optional<User> optionalFollowingUser = userService.getFollowingUser(followerUser, followingId);

            if (optionalFollowingUser.isPresent()) {

                User followingUser = optionalFollowingUser.get();
                followerUser.getFollowing().remove(followingUser);
                chinaDAO.merge(followerUser);
                ResponseEntity<List<User>> responseEntity =
                        new ResponseEntity<>(followerUser.getFollowing(), HttpStatus.OK);
                return responseEntity;

            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowers(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/followings/{userId}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowing(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/followers_amount/{userId}")
    public ResponseEntity<Integer> getAmountOFFollowers(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowers().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/followings_amount/{userId}")
    public ResponseEntity<Integer> getAmountOfFollowings(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            return new ResponseEntity<>(user.getFollowing().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
