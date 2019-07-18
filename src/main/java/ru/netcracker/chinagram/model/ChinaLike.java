package ru.netcracker.chinagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Entity
@AllArgsConstructor
public class ChinaLike extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private Photo photo;

    @ManyToOne(cascade = CascadeType.ALL)
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
