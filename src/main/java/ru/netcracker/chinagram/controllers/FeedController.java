package ru.netcracker.chinagram.controllers;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.exceptions.Errors;
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
@CrossOrigin(origins="http://localhost:4200")
public class FeedController {

    @Autowired
    private ChinaDAO chinaDAO;

    @GetMapping("/{userId}")
    public ResponseEntity getFeed(Pageable pageable, @PathVariable @NotNull String userId) {
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            if (user.getFollowing() != null) {
                List<Photo> feed = new ArrayList<>();
                user.getFollowing().forEach(e -> feed.addAll(e.getPhotos()));
                feed.sort(Comparator.comparing(AbstractEntity::getDate).reversed());
                Page<Photo> photoPage = new PageImpl<>(feed, pageable, feed.size());
                return new ResponseEntity<>(photoPage, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("user.getFollowing is null", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(String.format(Errors.USER_WITH_ID_NOT_FOUND, userId), HttpStatus.BAD_REQUEST);
        }
    }

    /**
    тестовые контроллеры
     **/

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

    @GetMapping("/test")
    public ResponseEntity<List<Photo>> getFeedListTest(){
        List<Photo> photos = chinaDAO.findAll(Photo.class);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

}
