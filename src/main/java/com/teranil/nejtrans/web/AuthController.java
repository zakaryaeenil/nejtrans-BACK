package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.dto.UserDTO;
import com.teranil.nejtrans.service.PasswordResetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordResetService passwordResetService;
    @PostMapping ("/resetpassword/{email}")
    public ResponseEntity<Boolean> resetPassword(@PathVariable String email) throws MessagingException, IOException {
        return passwordResetService.resetPassword(email);
    }

}
