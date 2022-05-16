package com.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.model.User;
import com.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> result = userRepository.findAll();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam(value= "email") String email) {

        User result = userRepository.getUserByEmail(email);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById")
    public ResponseEntity<User> getUserById(@RequestParam(value= "userID") long userID) {

        User result = userRepository.findById(userID).get();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    ResponseEntity<User> loginUser(@RequestBody User user) throws JSONException {

        User result = userRepository.getUserByEmail(user.getEmail());

        if (result != null && !result.isBanned()) {
            if (bCryptPasswordEncoder.matches(user.getPassword(), result.getPassword()))
            {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUser")
    @ResponseBody
    ResponseEntity<String> addUser(@RequestBody User user) throws JSONException {

        String email = user.getEmail();

            String emailInDB = "";
            try {
                emailInDB = userRepository.getUserByEmail(email).getEmail();
            }
            catch (Exception e) {}

            //If the email is present in the DB value will be changed from ""
            if (emailInDB.equals("")) {
                System.out.println(emailInDB);
                user.setDateCreated(new Date());
                userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>((new JSONObject().put("message", "Error").toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @PutMapping("/updateUser")
    ResponseEntity<User> updateUser(@RequestBody User user) throws JSONException {
            Optional<User> result = userRepository.findById(user.getId());

        if (result != null) {
            userRepository.save(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws JSONException {

        User temp =  userRepository.findById(id).get();

        if(temp != null) {
            userRepository.delete(userRepository.findById(id).get());

            return new ResponseEntity<>(new JSONObject().put("message", "Deleted").toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new JSONObject().put("message", "Error Not Found").toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/banUser")
    public ResponseEntity<User> banUser(@RequestParam(value= "userID") long userID) {

        Optional<User> result = userRepository.findById(userID);

        if (result != null) {
            User user= result.get();
            user.setBanned(true);
            userRepository.save(user);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unbanUser")
    public ResponseEntity<User> unbanUser(@RequestParam(value= "userID") long userID) {

        Optional<User> result = userRepository.findById(userID);

        if (result != null) {
            User user= result.get();
            user.setBanned(false);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


