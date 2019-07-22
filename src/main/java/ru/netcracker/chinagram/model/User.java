package ru.netcracker.chinagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "china_user")
@NoArgsConstructor
public class User extends AbstractEntity {

    public User(String username, String password, String info) {
        this.username = username;
        this.password = password;
        this.information = info;
    }

    private String username;

    private String password;

    private String information;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    List<Photo> photos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> followers = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "china_user_followers",
            joinColumns = @JoinColumn(name = "followers_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> following = new ArrayList<>();


    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    List<Like> likes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public List<User> getFollowers() {
        return followers;
    }


    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    @JsonIgnore
    public List<User> getFollowing() {
        return following;
    }


    public void setFollowing(List<User> following) {
        this.following = following;
    }

    @JsonIgnore
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @JsonIgnore
    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }



}
