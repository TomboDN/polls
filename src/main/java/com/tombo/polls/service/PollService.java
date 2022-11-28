package com.tombo.polls.service;

import com.tombo.polls.model.Option;
import com.tombo.polls.model.Poll;
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

    public void save(Poll poll) {
        pollRepository.save(poll);
    }

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public Poll findById(Long id) {
        return pollRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Poll id = " + id));
    }

    public void deleteById(Long id) {
        Poll poll = findById(id);
        if (voteRepository.existsByPoll(poll)){
            voteRepository.deleteAllByPoll(findById(id));
        } else pollRepository.deleteById(id);
    }

    public long countVotes(Poll poll){
        long counter = 0L;
        for (Option option : poll.getOptions()) {
            counter += option.getVoted();
        }
        return counter;
    }
}
