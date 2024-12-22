package com.example.userauthservice.Services;

import com.example.userauthservice.Exceptions.InvalidCrendentialsException;
import com.example.userauthservice.Exceptions.UserAlreadyExistException;
import com.example.userauthservice.Exceptions.UserNotFoundException;
import com.example.userauthservice.Repositories.UserRepository;
import com.example.userauthservice.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class AuthService {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
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
            String token = createJwtToken(user.get().getId(), new ArrayList<>(),user.get().getEmail());
            return token;
        }
        throw new InvalidCrendentialsException("invalid credentials");
    }

    public String createJwtToken(Long userID, ArrayList<String> roles, String email){
        Map<String , Object> dataJwt = new HashMap<>();

        dataJwt.put("userId",userID);
        dataJwt.put("roles",roles);
        dataJwt.put("email",email);

        String jwtToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expiration time (1 hour)
                .setClaims(dataJwt) // Custom claims (e.g., user role)
                .signWith(key) // Sign with the secret key
                .compact(); // Build the JWT

        return jwtToken;
    }

    public boolean validateToken(String token){
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key) // Use the same secret key
                    .build()
                    .parseClaimsJws(token); // Parse the token
        } catch (Exception e) {
            return false;
        }
        return true;


    }




}
