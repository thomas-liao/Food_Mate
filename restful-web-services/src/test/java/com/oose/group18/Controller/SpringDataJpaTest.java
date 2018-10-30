package com.oose.group18.Controller;


import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.Restaurant;
import com.oose.group18.Entity.User;
import com.oose.group18.Repository.PostRepository;
import com.oose.group18.Repository.RestaurantRepository;
import com.oose.group18.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpringDataJpaTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PostRepository postRepository;


    @Test
    public void findAllUserTest() throws Exception{
        List<User> temp =  userRepository.findAll();
        assert temp.size() == 10;
    }

    @Test
    public void findUsersByIdTest() {
        List<Integer> querryIds = new ArrayList<>();
        querryIds.add(1);
        querryIds.add(2);
        querryIds.add(3);
        List<User> temp =  userRepository.findAllById(querryIds);
        assert temp.size() == 3;
    }

    @Test
    public void checkUserNameTEST() {
        List<Integer> querryIds = new ArrayList<>();
        querryIds.add(1);
        querryIds.add(2);
        List<User> temp =  userRepository.findAllById(querryIds);
        assert temp.get(0).getUserName().equals("John");
    }

    @Test
    public void findAllRestaurantTest() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assert restaurants.size() == 280;
    }

    @Test
    public void findAllRestaurantByIdTest() {
        List<Integer> temp = new ArrayList<>();
        temp.add(1);
        temp.add(3);
        temp.add(5);
        List<Restaurant> restaurants = restaurantRepository.findAllById(temp);
        assert restaurants.get(0).getId() == 1;
        assert restaurants.get(1).getId() == 3;
        assert restaurants.get(2).getId() == 5;
    }
}