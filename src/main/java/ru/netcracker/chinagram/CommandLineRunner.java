package ru.netcracker.chinagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netcracker.chinagram.model.Like;
import ru.netcracker.chinagram.model.Comment;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.ChinaDAO;
import ru.netcracker.chinagram.repositories.UserRepository;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChinaDAO chinaDAO;


    @Override
    public void run(String... arg0) {
        User user1 = new User("Ivan", "123", "информация1");
        User user2 = new User("Vasia", "1234", "информация2");

        Photo photo = new Photo();
        photo.setUser(user1);
        photo.setImage("dsd");

        chinaDAO.persist(user1);
        chinaDAO.persist(user2);
        chinaDAO.merge(photo);

        Like like = new Like(photo, user2);
        chinaDAO.merge(like);

        user1.getFollowers().add(user2);
        chinaDAO.merge(user1);

        Comment comment = new Comment("fdfd", photo, user2);
        photo.getComments().add(comment);
        chinaDAO.merge(comment);



    }

}
