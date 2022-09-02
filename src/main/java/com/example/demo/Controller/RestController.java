package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/users")
public class RestController {

    @Autowired
    private UserRepo userRepo;

    public RestController(){}

    @GetMapping(value = "/")
    public ResponseEntity<?> getWelcome(){
        return new ResponseEntity<>("Welcome!", HttpStatus.OK);
    }

    @GetMapping(value = "/userInfo")
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping(value = "/userInfo/{id}")
    public User getUserById(@PathVariable(value = "id") int id){
        return userRepo.findById(id).get();
    }

    @PostMapping("/add")
    public String insertUser(@RequestBody User user){
        User user1 = new User();
        user1.setUserName(user.getUserName());
        user1.setUserEmail(user.getUserEmail());
        user1.setExperience(user.getExperience());
        user1.setDomain(user.getDomain());
        userRepo.save(user1);

        return "Record Inserted!";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable(value = "id") int id, @RequestBody User user){
        User user1 = userRepo.findById(id).get();

        user1.setUserName(user.getUserName());
        user1.setUserEmail(user.getUserEmail());
        user1.setExperience(user.getExperience());
        user1.setDomain(user.getDomain());
        userRepo.save(user1);

        return "Record Updated!";
    }

    @DeleteMapping("/delete/{id}")
    public List<User> deleteUser(@PathVariable(value = "id")int id){
        User user = userRepo.findById(id).get();
        userRepo.delete(user);

        return userRepo.findAll();
    }
}
