package com.tombo.polls.service;

import com.tombo.polls.model.Poll;
import com.tombo.polls.model.Vote;
import com.tombo.polls.repository.PollRepository;
import com.tombo.polls.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    public void save(Poll poll){
        pollRepository.save(poll);
    }

    public List<Poll> findAll(){
        return pollRepository.findAll();
    }

    public Poll findById(Long id){
        return pollRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Poll id = " + id));
    }

    public void deleteById(Long id){
        pollRepository.deleteById(id);
    }

    public Vote createVote(Long pollId, Vote vote){
        Poll poll = findById(pollId);

        vote.getOption().getId();
        return vote;
    }
}
