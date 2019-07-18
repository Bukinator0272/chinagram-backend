package ru.netcracker.chinagram.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    public AbstractEntity() {
        this.id = UUID.randomUUID();
        this.date = new Date();
    }

    @Id
    protected UUID id;

    protected Date date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
