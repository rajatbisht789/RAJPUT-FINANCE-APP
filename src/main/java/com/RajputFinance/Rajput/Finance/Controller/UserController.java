package com.RajputFinance.Rajput.Finance.Controller;

import com.RajputFinance.Rajput.Finance.Model.User;
import com.RajputFinance.Rajput.Finance.Service.UserService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/uploadUsers")
    public ResponseEntity<String> uploadUsers(@RequestParam("file") MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<User> usersToSave = new ArrayList<>();
            List<String> duplicatePhoneNumbers = new ArrayList<>(); // To track duplicates

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Extract data from each row
                String userName = row.getCell(0).getStringCellValue();
                String phoneNumber = row.getCell(1).getStringCellValue();
                boolean isAdmin = row.getCell(2).getBooleanCellValue();

                // Check if the phone number already exists
                if (userService.checkIfPhoneNumberAlreadyExisted(phoneNumber)) {
                    duplicatePhoneNumbers.add(phoneNumber);
                    continue; // Skip this user if the phone number is duplicate
                }

                // If not duplicate, create a new User and add it to the list
                User user = new User();
                user.setUserName(userName);
                user.setPhoneNumber(phoneNumber);
                user.setAdmin(isAdmin);
                usersToSave.add(user);
            }

            // Save all non-duplicate users in a single batch
            userService.saveAll(usersToSave);

            if (!duplicatePhoneNumbers.isEmpty()) {
                return ResponseEntity.ok("Some users were skipped due to duplicate phone numbers: " + duplicatePhoneNumbers);
            }
            return ResponseEntity.ok("Users uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload users");
        }
    }


    @PostMapping("/createNewUser")
    public ResponseEntity<User>  createNewUser(@RequestBody User userRequest){
        User userResponse = userService.createNewUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable Long id) {
        List<User> userResponse = userService.deleteUser(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/getUserByPhoneNumber/{phoneNumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        User userResponse = userService.getUserByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<User>>  getAllAdmin(){
        List<User> adminResponse = userService.getAllAdmin();
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<User>>  getAllCustomers(){
        List<User> response = userService.getAllCustomers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>>  getAllUser(){
        List<User> userResponse = userService.getAllUser();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user)
                .map(updatedUser -> ResponseEntity.ok(updatedUser))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateUserProfile")
    public ResponseEntity<User> updateUserProfile(
            @RequestParam Long id,
            @RequestParam String userName,
            @RequestParam String phoneNumber,
            @RequestParam Boolean admin,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        try {
            User updatedUser = userService.updateUser(id, userName, phoneNumber, admin, profileImage);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/uploads/{fileName}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get("uploads/" + fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // Adjust this depending on the image type
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/testing")
    public String testing() {
        return "User working";
    }

}
