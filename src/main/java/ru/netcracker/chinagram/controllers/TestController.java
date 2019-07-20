package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.exceptions.UserNotFoundException;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.ChinaDAO;

import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    ChinaDAO chinaDAO;

    @GetMapping(path = "/users/{id}")
    public User getUser(@PathVariable String id) throws UserNotFoundException {
       return chinaDAO.get(User.class, UUID.fromString(id));
    }

    @GetMapping(path = "/usernames/{username}")
    public User getUserByName(@PathVariable String username)   {
        return chinaDAO.get(User.class, "username", username);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
         chinaDAO.persist(user);
         return user;
    }

    @DeleteMapping("/users/remove")
    public void removeUser(@RequestBody User user){
        chinaDAO.remove(user);
    }



}
