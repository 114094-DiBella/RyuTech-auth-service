package ryutech.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ryutech.authservice.dtos.UserDto;
import ryutech.authservice.dtos.UserRequest;
import ryutech.authservice.models.User;
import ryutech.authservice.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody UserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody UserDto request) {
        User user = userService.changePassword(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
