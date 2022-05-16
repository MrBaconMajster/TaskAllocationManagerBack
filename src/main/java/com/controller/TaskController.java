package com.controller;

import com.model.Task;
import com.model.User;
import com.repository.TaskRepository;
import com.repository.UserRepository;
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
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getAllTasks")
    public ResponseEntity<List<Task>> getAllTasks() {

        List<Task> result = taskRepository.findAll();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTaskByID")
    public ResponseEntity<Task> getTaskByID(@RequestParam(value= "id") long id) {

        Optional<Task> result = taskRepository.findById(id);

        if (result != null) {
            Task task = result.get();
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTasksForUser")
    public ResponseEntity<ArrayList<Task>> getTasksForUser(@RequestParam(value= "userID") long userID) {

        ArrayList<Task> result = taskRepository.getTasksByUserID(userID);

        if (result != null) {

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTasksAssignedForUser")
    public ResponseEntity<ArrayList<Task>> getTasksAssignedForUser(@RequestParam(value= "userID") long userID) {

        ArrayList<Task> result = taskRepository.getTasksByAssignedUserID(userID);

        if (result != null) {

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unAssignFromTask")
    public ResponseEntity<Task> unAssignFromTask(@RequestParam(value= "taskID") long taskID) {

        Optional<Task> result = taskRepository.findById(taskID);

        if (result != null) {
            Task task = result.get();
            task.setAssignedTo(-1);
            task.setAssignedToName("");
            task.setStatus("Unassigned");
            taskRepository.save(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/assignUserToTask")
    public ResponseEntity<Task> assignUserToTask(@RequestParam(value= "userID")  long userID, @RequestParam(value= "taskID") long taskID) {

        Optional<Task> result = taskRepository.findById(taskID);
        Optional<User> userData = userRepository.findById(userID);

        if (result != null) {
            Task task = result.get();
            User user = userData.get();
            task.setAssignedTo(userID);
            task.setAssignedToName(user.getFirstName()+" "+user.getLastName());
            task.setStatus("In Progress");
            taskRepository.save(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PostMapping("/addTask")
    @ResponseBody
    ResponseEntity<String> addTask(@RequestBody Task task) throws JSONException {

            if (task != null) {
                taskRepository.save(task);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>((new JSONObject().put("message", "Error").toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }


    @PutMapping("/updateTask")
    ResponseEntity<Task> updateTask(@RequestBody Task task) throws JSONException {
            Task resultTask = taskRepository.findById(task.getId()).get();

            taskRepository.save(task);
            Task result =  taskRepository.save(task);
        if (result != null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable long id) throws JSONException {

        Optional<Task> temp =  taskRepository.findById(id);

        if(temp != null) {
            taskRepository.delete(taskRepository.findById(id).get());

            return new ResponseEntity<>(new JSONObject().put("message", "Deleted").toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new JSONObject().put("message", "Error Not Found").toString(), HttpStatus.NOT_FOUND);
        }
    }
}


