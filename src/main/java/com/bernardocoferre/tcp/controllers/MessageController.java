package com.bernardocoferre.tcp.controllers;

import com.bernardocoferre.tcp.concerns.MessageType;
import com.bernardocoferre.tcp.model.Message;
import com.bernardocoferre.tcp.model.MessageReference;
import com.bernardocoferre.tcp.repositories.MessageReferenceRepository;
import com.bernardocoferre.tcp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository repository;

    @GetMapping(path = "")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("messages/index");

        List<Message> messages = this.repository.findAllByOrderByIdDesc();
        mv.addObject("messages", messages);

        return mv;
    }

    @GetMapping(path = "/{id}")
    public ModelAndView show(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("messages/show");

        Optional<Message> messageReference = this.repository.findById(id);
        messageReference.ifPresent(i -> mv.addObject("message", i));

        return mv;
    }

}
