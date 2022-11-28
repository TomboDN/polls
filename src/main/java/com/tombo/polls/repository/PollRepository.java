package com.tombo.polls.repository;

import com.tombo.polls.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {}