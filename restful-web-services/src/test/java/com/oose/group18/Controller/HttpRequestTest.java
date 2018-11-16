package com.oose.group18.Controller;


import com.oose.group18.Entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUserRequestAndHttpStatusTest() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/1",
                String.class)).contains("John").contains("first@example.com");

        ResponseEntity<String> response1 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/1", String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> response2 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/9999", String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> response3 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/abc", String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

//    @Test
//    public void getUserHttpStatusTest() throws Exception {
//
//
////        ResponseEntity<String> response2 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/9999", String.class);
////        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//
//
//
//
//
//    }
//
//    @Test
//    public void deleteUserRequestTest() throws Exception {
//        this.restTemplate.delete("http://localhost:" + port + "/user/1");
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/1",
//                String.class)).doesNotContain("John").doesNotContain("first@example.com");
//    }
//    @Test
//    public void putUserRequestTest() throws Exception {
//
//        Integer tempId1 = 11;
//        User mockUser1 = new User();
//        mockUser1.setId(tempId1);
//        mockUser1.setAddr("somewhere");
//        mockUser1.setFullName("ThomasL");
//        mockUser1.setUserName("tliao4");
//        mockUser1.setPassword("123cdeF!");
//        mockUser1.setEmail("tliao4@jhu.edu");
//        mockUser1.setDescription("abcdefg");
//
//
//        this.restTemplate.postForObject("http://localhost:" + port+ "/register", mockUser1, String.class);
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/11",
//                String.class)).contains("ThomasL").contains("tliao4@jhu.edu");
//    }
//



}