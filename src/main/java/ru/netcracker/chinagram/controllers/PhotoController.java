package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.ChinaDAO;
import java.util.UUID;

@RestController
public class PhotoController {

    @Autowired
    ChinaDAO chinaDAO;

    @PostMapping("/photos")
    public ResponseEntity <Photo> createPhoto(@RequestBody Photo photo) {
        chinaDAO.persist(photo);
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    @GetMapping("/photos/{photoId}")
    public ResponseEntity <Photo> getPhotoById (@PathVariable String photoId) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            return new ResponseEntity<>(photo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ("/photos/user/{photoId}")
    public ResponseEntity<User> getUserByPhotoId (@PathVariable String photoId) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            User user = photo.getUser();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/photos/remove/{photoId}")
    public ResponseEntity<Photo> removePhotoById (@PathVariable String photoId) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            chinaDAO.remove(photo);
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping ("photos/update/{photoId}")
    public ResponseEntity<Photo> updatePhotoById (@PathVariable String photoId, @RequestBody String updatePhoto) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            photo.setImage(updatePhoto);
            chinaDAO.merge(photo);
            return new ResponseEntity<>(photo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
