package com.foodMate.rest.webservices.restfulwebservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Controller
@RestController
public class HelloWorldController {
    // GET
    // URI - /hello-world
    // method - "Hello World"
    @RequestMapping(method= RequestMethod.GET, path="hello-world")
    // if it is get method, for simplicity we can write

    @GetMapping(path="hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

}
