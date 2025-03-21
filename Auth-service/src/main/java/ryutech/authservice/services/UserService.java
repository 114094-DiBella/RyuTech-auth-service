package ryutech.authservice.services;

import org.springframework.stereotype.Service;
import ryutech.authservice.dtos.LoginResponse;
import ryutech.authservice.dtos.UserDto;
import ryutech.authservice.dtos.UserRequest;
import ryutech.authservice.entities.UserEntity;
import ryutech.authservice.models.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> getAllUsers();

    Optional<UserEntity> findByEmail(String email);

    LoginResponse login(UserDto userDto);

    User createUser(UserRequest user);

    User changePassword(UserDto userDto);

}
