package ru.netcracker.chinagram.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @GetMapping
    public List<Photo> getFeed(User user){
        List<Photo> feed = new ArrayList<>();
        user.getFollowing().forEach(e -> feed.addAll(e.getPhotos()));
        return feed;
    }
}
