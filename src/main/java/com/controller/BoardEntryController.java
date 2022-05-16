package com.controller;

import com.model.BoardEntry;
import com.repository.BoardEntryRepository;
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
public class BoardEntryController {

    @Autowired
    BoardEntryRepository boardEntryRepository;

    @GetMapping("/getAllBoardEntries")
    public ResponseEntity<List<BoardEntry>> getAllBoardEntries() {

        List<BoardEntry> result = boardEntryRepository.findAll();

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBoardEntryByUserID")
    public ResponseEntity<BoardEntry> getBoardEntryByEmail(@RequestParam(value= "id") long id) {

        BoardEntry result = boardEntryRepository.getBoardEntryByUserID(id);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addBoardEntry")
    @ResponseBody
    ResponseEntity<String> addBoardEntry(@RequestBody BoardEntry boardEntry) throws JSONException {

            //If the email is present in the DB value will be changed from ""
            if (boardEntry != null) {
                boardEntryRepository.save(boardEntry);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>((new JSONObject().put("message", "Error").toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @PutMapping("/updateBoardEntry")
    ResponseEntity<BoardEntry> updateBoardEntry(@RequestBody BoardEntry boardEntry, @RequestParam(value="id") long id) throws JSONException {

            boardEntry.setId(id);
            BoardEntry result =  boardEntryRepository.save(boardEntry);
        if (result != null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteBoardEntry/{id}")
    public ResponseEntity<String> deleteBoardEntry(@PathVariable long id) throws JSONException {

        BoardEntry temp =  boardEntryRepository.findById(id).get();

        if(temp != null) {
            boardEntryRepository.delete(boardEntryRepository.findById(id).get());

            return new ResponseEntity<>(new JSONObject().put("message", "Deleted").toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new JSONObject().put("message", "Error Not Found").toString(), HttpStatus.NOT_FOUND);
        }
    }
}


