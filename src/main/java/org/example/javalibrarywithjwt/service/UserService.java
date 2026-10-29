package org.example.javalibrarywithjwt.service;

import jakarta.transaction.Transactional;
import org.example.javalibrarywithjwt.model.LoginRequest;
import org.example.javalibrarywithjwt.model.LoginResponse;
import org.example.javalibrarywithjwt.model.User;
import org.example.javalibrarywithjwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtEncoder jwtEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;

    }

    //Registrera en ny användare
    public User register (User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists!"
            );
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists!"
            );
        }

        user.setId(null);

        //Hashar lösenordet
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Nya användare kan inte välja ADMIN utan blir USER vid registrering
        user.setRole("USER");

        return userRepository.save(user);

    }

    //Metod letar upp anvöndare med hashat lösenord och skapar samt signerar token
    public LoginResponse login (LoginRequest loginRequest) {

    }

}
