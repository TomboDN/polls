package com.tombo.polls.service;

import com.tombo.polls.model.User;
import com.tombo.polls.model.Vote;
import com.tombo.polls.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;

    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    public boolean existsByUser(User user) {
        return voteRepository.existsByUser(user);
    }
}
