package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.exceptions.UserNotFoundException;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.ChinaDAO;

import java.util.UUID;

@RestController
public class UserController {//

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

    @DeleteMapping("/users/remove")
    public ResponseEntity<User> removeUser(@RequestBody User user) {//заработалопока не работаетт
        chinaDAO.persist(user);
        chinaDAO.remove(user);
        return new ResponseEntity<User>(HttpStatus.GONE);
    }

    @DeleteMapping("/users/remove/{id}")
    public ResponseEntity<User> removeUserById(@PathVariable String id) { //заработало
        User user = chinaDAO.get(User.class, UUID.fromString(id));
         chinaDAO.persist(user);
        chinaDAO.remove(user);
        return new ResponseEntity<User>(HttpStatus.GONE);
    }
}
