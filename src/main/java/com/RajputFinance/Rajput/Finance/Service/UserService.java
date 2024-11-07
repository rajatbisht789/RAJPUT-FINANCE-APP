package com.RajputFinance.Rajput.Finance.Service;

import com.RajputFinance.Rajput.Finance.Model.User;
import com.RajputFinance.Rajput.Finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User createNewUser(User userRequest) {
        return userRepository.save(userRequest);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<User> deleteUser(Long id) {
        userRepository.deleteById(id);
        return getAllUser();
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}
