package ryutech.authservice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ryutech.authservice.dtos.LoginResponse;
import ryutech.authservice.dtos.UserDto;
import ryutech.authservice.dtos.UserRequest;
import ryutech.authservice.entities.UserEntity;
import ryutech.authservice.models.User;
import ryutech.authservice.repositories.UserJpaRepository;
import ryutech.authservice.services.UserService;
import ryutech.authservice.config.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        try {
            List<UserEntity> users = userJpaRepository.findAll();
            if (users.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
            }
            return users.stream()
                    .map(userEntity -> modelMapper.map(userEntity, User.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users", e);
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            Optional<UserEntity> user = userJpaRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching user by email", e);
        }
    }

    @Override
    public LoginResponse login(UserDto userDto) {
        try {
            Optional<UserEntity> userOptional = userJpaRepository.findByEmail(userDto.getEmail());

            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            UserEntity userEntity = userOptional.get();

            // Use passwordEncoder to compare passwords securely
            if (!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
            }

            String token = jwtUtil.generateToken(userEntity.getEmail());

            return new LoginResponse(token, "Login exitoso");

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during login", e);
        }
    }

    @Override
    public User createUser(UserRequest user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        Optional<UserEntity> userOptional = userJpaRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setId(UUID.randomUUID());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userJpaRepository.save(userEntity);
        return modelMapper.map(userEntity, User.class);

    }

    @Override
    public User changePassword(UserDto userDto) {
        Optional<UserEntity> userOptional = userJpaRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userJpaRepository.save(userEntity);
            return modelMapper.map(userEntity, User.class);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
}