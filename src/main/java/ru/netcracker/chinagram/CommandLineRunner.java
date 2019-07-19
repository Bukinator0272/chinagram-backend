package ru.netcracker.chinagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netcracker.chinagram.model.Photo;
import ru.netcracker.chinagram.model.User;
import ru.netcracker.chinagram.repositories.UserRepository;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... arg0) {
        User user1 = new User("Ivan", "123", "информация1");
        User user2 = new User("Vasia", "1234", "информация2");
        Photo photo = new Photo();
        photo.setUser(user1);
        photo.setImage("dsd");
        user1.getPhotos().add(photo);
        userRepository.save(user1);
        userRepository.save(user2);
    }

}
