package com.teranil.nejtrans.web;

import com.teranil.nejtrans.service.PasswordResetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordResetService passwordResetService;
    @PutMapping("/resetpassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email){
        return passwordResetService.resetPassword(email);
    }

}
