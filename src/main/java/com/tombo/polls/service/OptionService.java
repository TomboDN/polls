package com.tombo.polls.service;

import com.tombo.polls.model.Option;
import com.tombo.polls.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OptionService {
    private final OptionRepository optionRepository;

    public void save(Option option){
        optionRepository.save(option);
    }
}
