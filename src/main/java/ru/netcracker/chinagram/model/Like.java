package ru.netcracker.chinagram.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "china_like")
@AllArgsConstructor
@NoArgsConstructor
public class Like extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @NotNull
    private Photo photo;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @NotNull
    private User user;


    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
