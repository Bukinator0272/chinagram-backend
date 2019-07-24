package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.model.Like;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.ChinaDAO;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class LikeController {

    @Autowired
    ChinaDAO chinaDAO;

    @PostMapping ("/likes")
    public ResponseEntity<Like> createLike(@RequestBody Like like) {
        chinaDAO.persist(like);
        return new ResponseEntity<>(like, HttpStatus.CREATED);
    }

    @DeleteMapping ("/likes/delete/{photoId}/{userId}")
    public ResponseEntity<Like> createLike(@PathVariable String photoId, @PathVariable String userId) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (photo != null && user != null) {
            Like like = new Like(photo, user);
            chinaDAO.persist(like);
            return new ResponseEntity<>(like, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping (path = "/likes/photo/users/{photoID}")
    public ResponseEntity<ArrayList<User>> getListLikeByPhotoID (@PathVariable String photoID) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoID));
        if (photo != null) {
            ArrayList<User> users = new ArrayList<>();
            for (int i=0; i<photo.getLikes().size(); ++i){
                users.add(photo.getLikes().get(i).getUser());
            }
            return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/likes/{likeId}")
    public ResponseEntity<Like> getLikeById(@PathVariable String likeId) {
        Like like = chinaDAO.get(Like.class, UUID.fromString(likeId));
        if (like != null) {
            return new ResponseEntity<>(like, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping (path = "/count/likes/{photoId}")
    public ResponseEntity<Integer> getAmountOfLikesByPhotoId(@PathVariable String photoId) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            return new ResponseEntity<> (photo.getLikes().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping (path = "/likes/user/{likeId}")
    public ResponseEntity<User> getUserByLikeId (@PathVariable String likeId) {
        Like like = chinaDAO.get(Like.class, UUID.fromString(likeId));
        if (like != null) {
            User user = like.getUser();
            return new ResponseEntity<>(user, HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/likes/remove/{likeId}")
    public ResponseEntity<Like> removeLikeById(@PathVariable String likeId) {
        Like like = chinaDAO.get(Like.class, UUID.fromString(likeId));
        if (like != null) {
            chinaDAO.remove(like);
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}

