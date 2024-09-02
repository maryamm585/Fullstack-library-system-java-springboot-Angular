package com.task2.task2.Services;

import com.task2.task2.Entities.Book;
import com.task2.task2.Entities.Role;
import com.task2.task2.Entities.User;
import com.task2.task2.Repos.roleRepo;
import com.task2.task2.Repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {

    @Autowired
    private userRepo userRepository;

    @Autowired
    private roleRepo roleRepository;
    @Autowired
    private com.task2.task2.Repos.userRepo userRepo;

    public User createuser (User user) {

        return userRepository.save(user);
    }

    public List<User> searchByUsername(String username) {
        return userRepository.findByUsernameContaining(username);
    }

    public User getuserbyusername (String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getallusers() {
        return userRepository.findAll();
    }



}
