package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.netcracker.chinagram.model.AbstractEntity;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.services.interfaces.ChinaDAO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private ChinaDAO chinaDAO;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<Photo>> getFeed(Pageable pageable, @PathVariable @NotNull String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null && user.getFollowing() != null) {
            List<Photo> feed = new ArrayList<>();
            user.getFollowing().forEach(e -> feed.addAll(e.getPhotos()));
            feed.sort(Comparator.comparing(AbstractEntity::getDate).reversed());
            Page<Photo> photoPage = new PageImpl<>(feed, pageable, feed.size());
            return new ResponseEntity(photoPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/test/{userId}")
    public ResponseEntity<List<Photo>> getFeed(@PathVariable String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null && user.getFollowing() != null) {
            List<Photo> feed = new ArrayList<>();
            feed.sort(Comparator.comparing(AbstractEntity::getDate).reversed());
            user.getFollowing().forEach(e -> feed.addAll(e.getPhotos()));
            return new ResponseEntity(feed, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
