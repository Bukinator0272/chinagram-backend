package ru.netcracker.chinagram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netcracker.chinagram.model.Comment;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.ChinaDAO;

import java.util.List;
import java.util.UUID;

@RestController
public class CommentController {

    @Autowired
    ChinaDAO chinaDAO;

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        chinaDAO.persist(comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getComment (@PathVariable String commentId) {
        Comment comment= chinaDAO.get(Comment.class, UUID.fromString(commentId));
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping (path = "/comments/photo/users/{photoId}")
    public ResponseEntity<List<Comment>> getListCommentByPhotoId (@PathVariable String photoId) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            return new ResponseEntity<>(photo.getComments(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/comments/user/{userId}")
    public ResponseEntity <User> getUserByCommentId (@PathVariable String userId) {
        Comment comment = chinaDAO.get(Comment.class, UUID.fromString(userId));
        if (comment != null) {
            User user = comment.getUser();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ("/count/comments/{photoId}")
    public ResponseEntity<Integer> getAmountOfCommentsByPhotoId(@PathVariable String photoId) {
        Photo photo = chinaDAO.get(Photo.class, UUID.fromString(photoId));
        if (photo != null) {
            return new ResponseEntity<> (photo.getComments().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping ("/comments/update/{commentId}")
    public ResponseEntity<Comment> updateCommentById (@PathVariable String commentId, @RequestBody String updateContent) {
        Comment comment = chinaDAO.get(Comment.class, UUID.fromString(commentId));
        if (comment != null) {
            comment.setContent(updateContent);
            chinaDAO.merge(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comments/remove/{commentId}")
    public ResponseEntity<Comment> removeCommentById (@PathVariable String commentId) {
        Comment comment = chinaDAO.get(Comment.class, UUID.fromString(commentId));
        if (comment != null) {
            chinaDAO.remove(comment);
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}




