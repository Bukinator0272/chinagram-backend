package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.services.interfaces.ChinaDAO;

import java.util.UUID;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private ChinaDAO chinaDAO;

    @GetMapping("/{photoId}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable String photoId) { //working
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            return new ResponseEntity<>(photo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Photo> createPhotoByUserId(@PathVariable String userId, @RequestBody String image) { //working
        User user = chinaDAO.get(User.class, UUID.fromString(userId));
        if (user != null) {
            Photo photo = new Photo();
            photo.setUser(user);
            photo.setImage(image);
            chinaDAO.persist(photo);
            return new ResponseEntity<>(photo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{photoId}")
    public ResponseEntity<User> getUserByPhotoId(@PathVariable String photoId) { //working
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            User user = photo.getUser();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove/{photoId}")
    public ResponseEntity<Photo> removePhotoById(@PathVariable String photoId) { //not working
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            chinaDAO.remove(photo);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{photoId}")
    public ResponseEntity<Photo> updatePhotoById(@PathVariable String photoId, @RequestBody String updatePhoto) { //working
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            photo.setImage(updatePhoto);
            chinaDAO.merge(photo);
            return new ResponseEntity<>(photo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
