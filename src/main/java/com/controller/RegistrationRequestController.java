package com.controller;

import com.email.EmailSenderService;
import com.model.RegistrationRequest;
import com.repository.RegistrationRequestRepository;
import com.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class RegistrationRequestController {

    @Autowired
    RegistrationRequestRepository regRequestRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/getAllRegistrationRequests")
    public ResponseEntity<List<RegistrationRequest>> getAllRegistrationRequests() {

        List<RegistrationRequest> result = regRequestRepo.findAll();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllPendingRegistrationRequests")
    public ResponseEntity<List<RegistrationRequest>> getAllPendingRegistrationRequests() {

        List<RegistrationRequest> result = regRequestRepo.getAllPendingRegistrationRequests();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addRegistrationRequest")
    @ResponseBody
    ResponseEntity<String> addRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) throws JSONException {

        String email = registrationRequest.getEmail();

            String emailInDB = "";
            try {
                emailInDB = userRepository.getUserByEmail(email).getEmail();
                if (emailInDB.equals(""))
                {
                    emailInDB = regRequestRepo.getRegistrationRequestByEmail(email).getEmail();
                }
            }
            catch (Exception e) {}

            //If the email is present in the DB value will be changed from ""
            if (emailInDB.equals("")) {

                registrationRequest.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
                registrationRequest.setDateCreated(new Date());
                senderService.sendEmail(registrationRequest.getEmail(), "LERO Registration Request","Registration request to CSIS Task Manager has been sent and is awaiting approval.");
                regRequestRepo.save(registrationRequest);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>((new JSONObject().put("message", "Error").toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @PutMapping("/updateRegistrationRequest")
    ResponseEntity<RegistrationRequest> updateRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) throws JSONException {
            Optional<RegistrationRequest> result = regRequestRepo.findById(registrationRequest.getId());

        if (result != null) {
            regRequestRepo.save(registrationRequest);
            return new ResponseEntity<>(registrationRequest,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteRegistrationRequest/{id}")
    public ResponseEntity<String> deleteRegistrationRequest(@PathVariable long id) throws JSONException {

        RegistrationRequest temp =  regRequestRepo.findById(id).get();

        if(temp != null) {
            regRequestRepo.delete(regRequestRepo.findById(id).get());

            return new ResponseEntity<>(new JSONObject().put("message", "Deleted").toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new JSONObject().put("message", "Error Not Found").toString(), HttpStatus.NOT_FOUND);
        }
    }


}


