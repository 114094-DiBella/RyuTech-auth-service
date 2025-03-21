package ryutech.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ryutech.authservice.dtos.LoginResponse;
import ryutech.authservice.dtos.UserDto;
import ryutech.authservice.services.UserService;



@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDto request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }


}
