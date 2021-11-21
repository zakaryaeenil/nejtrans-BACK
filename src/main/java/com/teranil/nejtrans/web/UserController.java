package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import com.teranil.nejtrans.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/api/users")
    public List<UserDTO> getAll(){
        return userService.getAll();
    }
}
