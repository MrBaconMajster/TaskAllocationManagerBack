package com.controller;

import com.model.TaskNamesList;
import com.repository.TaskNamesListRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class TaskNamesListController {

    @Autowired
    TaskNamesListRepository taskNamesListRepository;

    @GetMapping("/getAllTaskNamesList")
    public ResponseEntity<List<TaskNamesList>> getAllTaskNamesLists() {

        List<TaskNamesList> result = taskNamesListRepository.findAll();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addTaskNamesList")
    @ResponseBody
    ResponseEntity<String> addTaskNamesList(@RequestBody TaskNamesList taskName) throws JSONException {

            if (taskName != null) {
                taskNamesListRepository.save(taskName);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>((new JSONObject().put("message", "Error").toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @DeleteMapping("/deleteTaskNamesList/{id}")
    public ResponseEntity<String> deleteTaskNamesList(@PathVariable long id) throws JSONException {

        TaskNamesList temp =  taskNamesListRepository.findById(id).get();

        if(temp != null) {
            taskNamesListRepository.delete(taskNamesListRepository.findById(id).get());

            return new ResponseEntity<>(new JSONObject().put("message", "Deleted").toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new JSONObject().put("message", "Error Not Found").toString(), HttpStatus.NOT_FOUND);
        }
    }
}


