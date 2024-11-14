package com.RajputFinance.Rajput.Finance.Service;

import com.RajputFinance.Rajput.Finance.Model.User;
import com.RajputFinance.Rajput.Finance.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final String UPLOAD_DIR = "uploads/";

    @PostConstruct
    public void init() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // Create the directory if it doesn't exist
        }
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public User createNewUser(User userRequest) {
        if(checkIfPhoneNumberAlreadyExisted(userRequest.getPhoneNumber()))
            return userRequest;
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

    public boolean checkIfPhoneNumberAlreadyExisted(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public List<User> getAllAdmin() {
        return userRepository.findAllByIsAdmin(true);
    }

    public Optional<User> updateUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUserName(user.getUserName());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            updatedUser.setAdmin(user.isAdmin());
            return Optional.of(userRepository.save(updatedUser));
        } else {
            return Optional.empty();
        }
    }

    public User updateUser(Long userId, String userName, String phoneNumber, Boolean admin, MultipartFile profileImage) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(userName);
        user.setPhoneNumber(phoneNumber);

        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, profileImage.getBytes());
            user.setProfileImage(fileName);
        }

        return userRepository.save(user);
    }

    public List<User> getAllCustomers() {
        return userRepository.findAllByIsAdmin(false);
    }
}
