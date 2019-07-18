package ru.netcracker.chinagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Photo extends AbstractEntity {

    private String image;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    private List<ChinaLike> likes = new ArrayList<>();


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<ChinaLike> getLikes() {
        return likes;
    }

    public void setLikes(List<ChinaLike> likes) {
        this.likes = likes;
    }


}
