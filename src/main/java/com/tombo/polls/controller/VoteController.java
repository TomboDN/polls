package com.tombo.polls.controller;

import com.tombo.polls.model.Vote;
import com.tombo.polls.repository.VoteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class VoteController {
    private final VoteRepository voteRepository;

    @PostMapping("/polls/{id}/votes")
    public ResponseEntity<?> createVote(@PathVariable Long id, @Valid Vote vote){

        voteRepository.save(vote);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
