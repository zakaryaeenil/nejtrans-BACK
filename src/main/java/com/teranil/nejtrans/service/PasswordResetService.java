package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class PasswordResetService {
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<Boolean> resetPassword(String email) {
     //   String code = alphaNumericString(8);
        String code = "password";
        if (userRepository.existsByEmail(email)){
            User user = userRepository.findByEmail(email);
            // mailSenderService.SendEmail(user.getEmail(), "Password reset!", "Your password has been reset, your new password is : "+code);
            user.setPassword(bCryptPasswordEncoder.encode(code));
            userRepository.save(user);
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);
    }
    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

   /* public ResponseEntity<String> resetPassword(HelperClass.PasswordResetForm passwordResetForm) {
        User user = userRepository.findByEmail(passwordResetForm.getEmail());
        if (LocalDateTime.now().isAfter(user.getResetPasswordTokenExpirationDate()) || !Objects.equals(user.getResetPasswordToken(), passwordResetForm.getCode())) {
            return ResponseEntity.badRequest().body("Token expired or Wrong Token!");
        }
        if (Objects.equals(passwordResetForm.getConfirmPassword(), passwordResetForm.getNewPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(passwordResetForm.getNewPassword()));
            user.setResetPasswordToken("");
            user.setResetPasswordTokenExpirationDate(null);
            return ResponseEntity.ok().body("Password has been reset");
        }
        return ResponseEntity.badRequest().body("Make sure the password is correct");
    }*/
}
