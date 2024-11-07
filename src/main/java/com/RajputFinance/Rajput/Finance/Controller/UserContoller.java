package com.RajputFinance.Rajput.Finance.Controller;

import com.RajputFinance.Rajput.Finance.Model.User;
import com.RajputFinance.Rajput.Finance.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserContoller {

    @Autowired
    private UserService userService;

    @PostMapping("/createNewUser")
    public ResponseEntity<User>  createNewUser(@RequestBody User userRequest){
        User userResponse = userService.createNewUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable Long id) {
        List<User> userResponse = userService.deleteUser(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/getUserByPhoneNumber/{phoneNumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        User userResponse = userService.getUserByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>>  getAllUser(){
        List<User> userResponse = userService.getAllUser();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/testing")
    public String testing() {
        return "working";
    }
}
