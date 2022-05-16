package com.controller;

import com.model.BoardEntry;
import com.model.UserTaskInterest;
import com.repository.TaskNamesListRepository;
import com.repository.UserTaskInterestRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class UserTaskInterestController {

    @Autowired
    UserTaskInterestRepository userTaskInterestRepository;

    @GetMapping("/getTaskInterestListForUser")
    public ResponseEntity<List<UserTaskInterest>> getTaskInterestListForUser(@RequestParam(value= "userID") long id) {

        List<UserTaskInterest> result = userTaskInterestRepository.getUserTaskInterestListForUser(id);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUsersInterestedInTask")
    public ResponseEntity<List<UserTaskInterest>> getUsersInterestedInTask(@RequestParam(value= "taskNameID") long id) {

        List<UserTaskInterest> result = userTaskInterestRepository.getUsersInterestedInTask(id);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addTaskInterestsForUser")
    @ResponseBody
    ResponseEntity<String> addTaskNamesList(@RequestBody List<UserTaskInterest> userTaskInterestList) throws JSONException {

            if (userTaskInterestList != null) {
                for (UserTaskInterest taskInterest : userTaskInterestList)
                {
                    userTaskInterestRepository.save(taskInterest);
                }

                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>((new JSONObject().put("message", "Error").toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @DeleteMapping("/deleteTaskInterestListForUser/{id}")
    public ResponseEntity<String> deleteTaskInterestListForUser(@PathVariable long id) throws JSONException {

        List<UserTaskInterest> temp = userTaskInterestRepository.getUserTaskInterestListForUser(id);

        if(temp != null) {
            for (UserTaskInterest taskInterest : temp)
            {
                userTaskInterestRepository.delete(taskInterest);
            }

            return new ResponseEntity<>(new JSONObject().put("message", "Deleted").toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new JSONObject().put("message", "Error Not Found").toString(), HttpStatus.NOT_FOUND);
        }
    }
}


