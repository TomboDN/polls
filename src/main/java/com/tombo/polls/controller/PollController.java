package com.tombo.polls.controller;

import com.tombo.polls.model.Option;
import com.tombo.polls.model.Poll;
import com.tombo.polls.model.Vote;
import com.tombo.polls.service.OptionService;
import com.tombo.polls.service.PollService;
import com.tombo.polls.service.UserService;
import com.tombo.polls.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.Instant;

@Controller
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;
    private final VoteService voteService;
    private final UserService userService;
    private final OptionService optionService;

    @GetMapping("/polls/create")
    public String showPollCreationForm(Model model) {
        model.addAttribute("poll", new Poll());
        return "poll-create";
    }
    @RequestMapping(value = "/polls", params = {"addOption"})
    public String addOption(final Poll poll, final BindingResult bindingResult){
        poll.getOptions().add(new Option());
        return "poll-create";
    }
    @RequestMapping(value = "/polls", params = {"removeOption"})
    public String removeOption(final Poll poll, final BindingResult bindingResult, final HttpServletRequest request){
        final long optionId = Long.parseLong(request.getParameter("removeOption"));
        poll.getOptions().remove((int) optionId);
        return "poll-create";
    }

    @RequestMapping(value = "/polls", params = {"save"})
    public String createPoll(final @Valid Poll poll, final BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()){
            return "redirect:/polls/create";
        }
        poll.setCreationDateTime(Instant.now());
        for (Option option : poll.getOptions()) {
//            option.setPoll(poll);
            option.setVoted(0L);
        }
        poll.setCreator(userService.findUserByUsername(principal.getName()));
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
    public String getPollToVote(@PathVariable Long id, Model model, Principal principal) {
        Poll poll = pollService.findById(id);
        if (poll != null) {
            if (poll.isMultipleAttempts()){
            } else if (voteService.existsByUser(userService.findUserByUsername(principal.getName()))) {
                return "redirect:/polls/" + id + "/result";
            }
            model.addAttribute("poll", poll);
            model.addAttribute("vote", new Vote(poll, userService.findUserByUsername(principal.getName())));
            return "poll";
        } else return "polls-list";
    }

    @PostMapping("/polls/{id}/votes")
    public String createVote(@PathVariable Long id, @Valid Vote vote, Principal principal) {
        vote.setPoll(pollService.findById(id));
        vote.setUser(userService.findUserByUsername(principal.getName()));
        voteService.save(vote);
        for (Option option : vote.getOptions()) {
            if (option != null){
                option.incrementVotes();
                optionService.save(option);
            }
        }
        return "redirect:/polls/" + id.toString();
    }

    @GetMapping("/polls/{id}/result")
    public String getPollResult(@PathVariable Long id, Model model) {
        Poll poll = pollService.findById(id);
        model.addAttribute("poll", poll);
        model.addAttribute("total_votes", pollService.countVotes(poll));
        return "poll-result";
    }

    @GetMapping("/polls/delete/{id}")
    public String deletePoll(@PathVariable Long id) {
        pollService.deleteById(id);
        return "index";
    }
}
