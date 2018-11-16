package com.oose.group18.Controller;


import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.Restaurant;
import com.oose.group18.Entity.User;
import com.oose.group18.Repository.PostRepository;
import com.oose.group18.Repository.RestaurantRepository;
import com.oose.group18.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

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

    @Test
    public void findOneNonExistentUserTest() {
        // no such user ids exist
        List<Integer> nonexistentQuerry = new ArrayList<>();
        nonexistentQuerry.add(999);

        List<User> usrs = userRepository.findAllById(nonexistentQuerry);
        // should return []
        assert usrs.size() == 0;
    }

    @Test
    public void addOneUserTest() {
        User mockUser = new User();
        Integer tempId = 7;
        mockUser.setId(tempId);
        mockUser.setAddr("somewhere");
        mockUser.setFullName("ThomasL");
        mockUser.setUserName("tliao4");
        mockUser.setPassword("123cdeF!");
        mockUser.setEmail("tliao4@jhu.edu");
        mockUser.setDescription("abcdefg");

        userRepository.save(mockUser);
        List<Integer> querry = new ArrayList<>();
        querry.add(7);

        List<User> res = userRepository.findAllById(querry);

        assert res.size() == 1;
        assert res.get(0).getId() == tempId;
        assert res.get(0).getUserName().equals("tliao4");
        assert res.get(0).getAddr().equals("somewhere");
        assert res.get(0).getPassword().equals("123cdeF!");
        assert res.get(0).getEmail().equals("tliao4@jhu.edu");
        assert res.get(0).getDescription().equals("abcdefg");
    }

    @Test
    public void addMultipleUsersTest() {
        User mockUser1 = new User();
        Integer tempId1 = 5;
        mockUser1.setId(tempId1);
        mockUser1.setAddr("somewhere");
        mockUser1.setFullName("ThomasL");
        mockUser1.setUserName("tliao4");
        mockUser1.setPassword("123cdeF!");
        mockUser1.setEmail("tliao4@jhu.edu");
        mockUser1.setDescription("abcdefg");

        User mockUser2 = new User();
        Integer tempId2 = 6;
        mockUser2.setId(tempId2);
        mockUser2.setAddr("somewhere2");
        mockUser2.setFullName("mxg");
        mockUser2.setUserName("abcde123");
        mockUser2.setPassword("321defgG!");
        mockUser2.setEmail("xxx@jhu.edu");
        mockUser2.setDescription("whatever");

        List<User> usrs = new ArrayList<>();
        usrs.add(mockUser1);
        usrs.add(mockUser2);
        System.out.println("Sanity check");
        System.out.println(usrs.size());

        List<Integer> querryIDs = new ArrayList<>();
        querryIDs.add(tempId1);
        querryIDs.add(tempId2);

        userRepository.saveAll(usrs);

        List<User> res = userRepository.findAllById(querryIDs);

        assert res.size() == 2;
        assert res.get(0).getId() == tempId1;
        assert res.get(1).getId() == tempId2;
    }

    @Test
    public void findMultipleNonExistentUsersTest() {
        List<Integer> nonexistentQuerry = new ArrayList<>();
        nonexistentQuerry.add(999);
        nonexistentQuerry.add(888);
        nonexistentQuerry.add(12345);
        nonexistentQuerry.add(3333);

        List<User> usrs = userRepository.findAllById(nonexistentQuerry);
        // should return []
        assert usrs.size() == 0;
    }

    @Test
    public void findExistNonExistUsersTest() {
        Integer tempId1 = 5;
        Integer tempId2 = 6;
        Integer tempId3 = 111;
        Integer tempId4 = 222;

        User mockUser1 = new User();
        mockUser1.setId(tempId1);
        mockUser1.setAddr("somewhere");
        mockUser1.setFullName("ThomasL");
        mockUser1.setUserName("tliao4");
        mockUser1.setPassword("123cdeF!");
        mockUser1.setEmail("tliao4@jhu.edu");
        mockUser1.setDescription("abcdefg");

        User mockUser2 = new User();
        mockUser2.setId(tempId2);
        mockUser2.setAddr("somewhere2");
        mockUser2.setFullName("mxg");
        mockUser2.setUserName("abcde123");
        mockUser2.setPassword("321defgG!");
        mockUser2.setEmail("xxx@jhu.edu");
        mockUser2.setDescription("whatever");

        List<User> usrs = new ArrayList<>();
        usrs.add(mockUser1);
        usrs.add(mockUser2);
        System.out.println("Sanity check");
        System.out.println(usrs.size());

        List<Integer> querryIDs = new ArrayList<>();
        querryIDs.add(tempId1);
        querryIDs.add(tempId2);
        querryIDs.add(tempId3);
        querryIDs.add(tempId4);

        userRepository.saveAll(usrs);

        List<User> res = userRepository.findAllById(querryIDs);

        assert res.size() == 2; // should only return 2 instead of 4, although querry for 4 ids.w
        assert res.get(0).getId() == tempId1;
        assert res.get(1).getId() == tempId2;
    }
}