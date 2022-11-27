package com.tombo.polls.service;

import com.tombo.polls.model.Role;
import com.tombo.polls.model.User;
import com.tombo.polls.repository.RoleRepository;
import com.tombo.polls.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User already exists for this username");
        } else {
            Optional<Role> role_user = roleRepository.findByName("ROLE_USER");
            role_user.ifPresent(role -> user.getRoles().add(role));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
}
