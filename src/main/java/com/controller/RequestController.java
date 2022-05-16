package com.controller;

import com.model.Request;
import com.repository.RequestRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.platform.commons.function.Try;
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
public class RequestController {

    @Autowired
    RequestRepository requestRepository;

    @GetMapping("/getAllRequests")
    public ResponseEntity<List<Request>> getAllRequests() {

        List<Request> result = requestRepository.findAll();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getRequestByID")
    public ResponseEntity<Request> getRequestByID(@RequestParam(value= "id") long id) {

        Optional<Request> result = requestRepository.findById(id);

        if (result != null) {
            Request request = result.get();
            return new ResponseEntity<>(request, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getRequestsForUser")
    public ResponseEntity<ArrayList<Request>> getRequestsForUser(@RequestParam(value= "userID") long userID) {

        ArrayList<Request> result = requestRepository.getRequestsForUser(userID);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getRequestsSentByUser")
    public ResponseEntity<ArrayList<Request>> getRequestsSentByUser(@RequestParam(value= "userID") long userID) {

        ArrayList<Request> result = requestRepository.getPendingRequestsSentByUser(userID);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPendingRequestsForUser")
    public ResponseEntity<ArrayList<Request>> getPendingRequestsForUser(@RequestParam(value= "userID") long userID) {

        ArrayList<Request> result = requestRepository.getPendingRequestsForUser(userID, "Pending");

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addRequest")
    @ResponseBody
    ResponseEntity<String> addRequest(@RequestBody Request request) throws JSONException {
        long id = -11;
        try
        {
            Request duplicate = requestRepository.getDuplicateRequest(request.getReceiverId(), request.getTaskId());
            id = duplicate.getId();

        }
        catch(Exception e) {}

            if(request != null && id == -11){
                requestRepository.save(request);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>((new JSONObject().put("message", "Error").toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @PutMapping("/updateRequest")
    ResponseEntity<Request> updateRequest(@RequestBody Request request, @RequestParam(value="id") long id) throws JSONException {

            request.setId(id);
            Request result =  requestRepository.save(request);
        if (result != null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/setRequestToAccepted")
    ResponseEntity<Request> setRequestToAccepted(@RequestParam(value="requestID") long id) throws JSONException {

        Optional<Request> request = requestRepository.findById(id);

        if (request != null) {
            Request result = request.get();
            result.setRequestState("Accepted");
            ArrayList<Request> list = requestRepository.setOtherRequestsToRejected(result.getTaskId(),result.getReceiverId());
            requestRepository.save(result);

            list.forEach((o) -> o.setRequestState("Rejected"));
            list.forEach((o) -> requestRepository.save(o));

            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/setRequestToDeclined")
    ResponseEntity<Request> setRequestToDeclined(@RequestParam(value="requestID") long id) throws JSONException {

        Optional<Request> request = requestRepository.findById(id);

        if (request != null) {
            Request result = request.get();
            result.setRequestState("Declined");
            requestRepository.save(result);
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/setRequestToPending")
    ResponseEntity<Request> setRequestToPending(@RequestParam(value="requestID") long id) throws JSONException {

        Optional<Request> request = requestRepository.findById(id);

        if (request != null) {
            Request result = request.get();
            result.setRequestState("Pending");
            requestRepository.save(result);
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteRequest/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable long id) throws JSONException {

        Request temp =  requestRepository.findById(id).get();

        if(temp != null) {
            requestRepository.delete(requestRepository.findById(id).get());

            return new ResponseEntity<>(new JSONObject().put("message", "Deleted").toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new JSONObject().put("message", "Error Not Found").toString(), HttpStatus.NOT_FOUND);
        }
    }
}


