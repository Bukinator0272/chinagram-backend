package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.Exceptions.UserNotFoundException;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.UserRepository;

import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/users/{id}")
    public User getUser(@PathVariable String id) throws UserNotFoundException {
       return userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new UserNotFoundException(id));
    }


    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
}
