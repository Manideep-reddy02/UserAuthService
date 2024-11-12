package com.example.userauthservice.Services;

import com.example.userauthservice.Exceptions.InvalidCrendentialsException;
import com.example.userauthservice.Exceptions.UserAlreadyExistException;
import com.example.userauthservice.Exceptions.UserNotFoundException;
import com.example.userauthservice.Repositories.UserRepository;
import com.example.userauthservice.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public  AuthService(UserRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean signUp(String email, String password) throws UserAlreadyExistException {
        if(userRepository.findByEmail(email).isPresent()){
            throw new UserAlreadyExistException("user with "+email+" already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    public String login(String email,String password) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException(email+" is not registered please sign up to login");
        }
       boolean passwordCheck = bCryptPasswordEncoder.matches(password,user.get().getPassword());
        boolean emailCheck = user.get().getEmail().equals(email);
        if(passwordCheck&&emailCheck){
            return "token";
        }
        throw new InvalidCrendentialsException("invalid credentials");
    }


}
