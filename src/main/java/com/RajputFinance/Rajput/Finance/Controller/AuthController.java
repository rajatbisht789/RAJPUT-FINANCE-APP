package com.RajputFinance.Rajput.Finance.Controller;

import com.RajputFinance.Rajput.Finance.Utils.JwtUtil;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody LoginRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String jwtToken = jwtUtil.generateToken(phoneNumber);
        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }
}

@Data
class LoginRequest {
    private String phoneNumber;

}

@Getter
class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
