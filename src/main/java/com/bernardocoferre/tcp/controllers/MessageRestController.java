package com.bernardocoferre.tcp.controllers;

import com.bernardocoferre.tcp.model.Message;
import com.bernardocoferre.tcp.model.MessageReference;
import com.bernardocoferre.tcp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired
    private MessageRepository repository;

    @GetMapping(path = "")
    public ResponseEntity<List<Message>> index() {
        List<Message> messages = this.repository.findAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<Message>> show(@PathVariable Integer id) {
        try {
            Optional<Message> message = this.repository.findById(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Optional<Message>> destroy(@PathVariable Integer id) {
        try {
           this.repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Optional<Message>> destroy() {
        this.repository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
