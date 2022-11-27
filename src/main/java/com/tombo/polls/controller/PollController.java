package com.tombo.polls.controller;

import com.tombo.polls.model.Poll;
import com.tombo.polls.service.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;

    @GetMapping("/polls/create")
    public String showPollCreationForm(Model model){
        model.addAttribute("poll", new Poll());
        return "poll-create";
    }

    @PostMapping("/polls")
    public String createPoll(@Valid Poll poll) {
        pollService.save(poll);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(poll.getId()).toUri();
        return "redirect:" + uri;
    }

    @GetMapping("/polls")
    public String getAllPolls(Model model) {
        model.addAttribute("polls", pollService.findAll());
        return "polls-list";
    }

    @GetMapping("/polls/{id}")
    public String getPoll(@PathVariable Long id, Model model) {
        Poll poll = pollService.findById(id);
        if (poll != null) {
            model.addAttribute("poll", pollService.findById(id));
            return "poll";
        } else return "polls-list";
    }
    @GetMapping("/polls/{id}/result")
    public String getPollResult(@PathVariable Long id, Model model){
        model.addAttribute("poll_result", pollService.findById(id));
        return "poll-result";
    }

    @PostMapping("/polls/{id}")
    public String updatePoll(@Valid Poll poll, @PathVariable Long id) {
        if (pollService.findById(id) != null) {
            return "polls-list";
        } else {
            pollService.save(poll);
            return "redirect:" + ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        }
    }

    @PostMapping("/polls/delete/{id}")
    public String deletePoll(@PathVariable Long id) {
        pollService.deleteById(id);
        return "polls-list";
    }
}
