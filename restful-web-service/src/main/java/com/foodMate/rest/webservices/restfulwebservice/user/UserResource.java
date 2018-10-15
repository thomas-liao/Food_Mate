package com.foodMate.rest.webservices.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// resource is actually a controller.. its resources gets exposed, so we specifically call it resource

@RestController
public class UserResource {
    // find repository automatically
    @Autowired
    private UserDaoService service;

    // retrive all users

    //retrive single user
    @GetMapping("/users")
    public List<User> retriveAllUsers() {
        return service.findALL();
    }

    @GetMapping("/users/{id}")
    public Resource<User> retriveUser(@PathVariable Integer id) {
        User user = service.findOne(id);
        // 404 not found
        if (user==null) {
            throw new UserNotFoundException("id-"+id);
        }
        // HATEOS - create link and expose resource
        Resource<User> resource = new Resource<User>(user);
        // controller link builder: allow us to create link from method
        ControllerLinkBuilder linkTo =
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retriveAllUsers());
        resource.add(linkTo.withRel("all-users")); // withRel: just set up a name for the link


        return resource; // instead of just returning data, we are returning resource(containing both data and link) -- HATEOS
    }

    @PostMapping("/users")
    // user - @requestbody - > (Json) user - > repository - POST-> repository.SaveUser.
    // @Valid: make sure the input information "make sense" - otherwise return bad request
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // if successfully created, do 2 things
        // 1. create URI for the location of the resource(servlet uri component builder) and response it
        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri(); //
        // basically what above line is doing is to, based on current request, create the uri by changing the {id} with savedUser.getId(), and
        // convert to uri (toUri())

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteUser(id);
        if (user == null) {
            throw new UserNotFoundException("id-"+id);
        }

    }
}
