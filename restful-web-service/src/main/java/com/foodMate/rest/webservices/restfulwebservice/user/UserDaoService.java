package com.foodMate.rest.webservices.restfulwebservice.user;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 5;
    static {
        users.add(new User(0, "Thomas", "Liao", "tliao4", new Date(), "Malone 360"));
        users.add(new User(1, "Xiangge", "Meng", "mxg", new Date(), "somewhere"));
        users.add(new User(2, "Yi", "Zhang", "zy", new Date(), "somewhere2"));
        users.add(new User(3, "Yujia", "Liu", "lyj", new Date(), "somewhere3"));
        users.add(new User(4, "Evan", "x", "evan", new Date(), "somewhere4"));
    }

    public List<User> findALL() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        this.users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user:users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User deleteUser(int id) {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
