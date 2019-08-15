package ru.netcracker.chinagram.services.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.services.impl.ChinaDAOImpl;

import java.util.List;


public interface FeedService {

  List<Photo> getPhotoList(User user, Integer maxResult,  Integer firstResult);

}
