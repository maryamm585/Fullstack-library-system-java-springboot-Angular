package com.task2.task2.Controllers;

import com.task2.task2.Entities.Role;
import com.task2.task2.Entities.User;
import com.task2.task2.Repos.userRepo;
import com.task2.task2.Services.JwtTokenProvider;
import com.task2.task2.Services.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/authentication")
public class authenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @Autowired
    private userRepo userrepo;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private roleService roleservice;

    @Autowired
    private PasswordEncoder passwordencoder;

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {
        try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = tokenProvider.createToken(username);

            User user = userrepo.findByUsername(username);
            // Prepare the response map
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getId());
            response.put("userRole", user.getRoles());
            response.put("jwtToken", jwtToken);



            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid user name or password");
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/Signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setEnabled(true);
        //Set<Role> roles = roleservice.getRolesByUsername(user.getUsername());
        user.setRoles(" ");
        //user.setRoles(roles);
        userrepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

}
