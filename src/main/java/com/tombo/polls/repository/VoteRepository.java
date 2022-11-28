package com.tombo.polls.repository;

import com.tombo.polls.model.Poll;
import com.tombo.polls.model.User;
import com.tombo.polls.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    void deleteAllByPoll(Poll poll);
    boolean existsByUser(User user);
    boolean existsByPoll(Poll poll);
}