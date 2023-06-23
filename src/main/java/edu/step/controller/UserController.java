package edu.step.controller;

import edu.step.model.User;
import edu.step.security.UserDetailsServiceImpl;
import edu.step.security.jwt.JwtResponse;
import edu.step.security.jwt.JwtTokenUtil;
import edu.step.service.UserService;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> getAuthToken(@RequestBody User user) {
        try {
            authenticate(user.getEmail(), user.getPassword());
        } catch (DisabledException | BadCredentialsException e) {
            Logger.warn(e);
            return ResponseEntity.badRequest().build();
        }

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(user.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> getAuthTokenAfterRegister(@RequestBody User user) {
        if (userService.addUser(new User(user)) == null) {
            return ResponseEntity.badRequest().build();
        }
        return getAuthToken(user);
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserByEmail(principal.getName()));
    }

    @PutMapping
    public ResponseEntity<?> updateCurrentUser(@RequestBody User user, Principal principal) {
        User userToUpdate = userService.getUserByEmail(principal.getName());
        if (userService.updateUserById(new User(user), userToUpdate.getId()) == null) {
            return ResponseEntity.badRequest().build();
        }
        return getAuthToken(user);
    }

    private void authenticate(
        String username,
        String password
    ) throws DisabledException, BadCredentialsException {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
    }
}
