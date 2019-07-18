package ru.netcracker.chinagram.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class User extends AbstractEntity {

    public User(String username, String password, String info){
        this.username = username;
        this.password = password;
        this.information = info;
    }

    private String username;

    private String password;

    private String information;

    @OneToMany
    List<Photo> photos = new ArrayList<>();

    @ManyToMany
    private List<User> followers  = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinTable(name = "user_followers",
            joinColumns = @JoinColumn(name = "followers_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> following = new ArrayList<>();


    @OneToMany
    List<ChinaLike> likes = new ArrayList<>();

    @OneToMany
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

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }


    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }



}
