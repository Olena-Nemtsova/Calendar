package edu.step.service;

import edu.step.model.User;
import edu.step.model.UserValidator;
import edu.step.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    private final UserValidator validator;

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user) {
        try {
            validator.validate(user);

            User existsUser = getUserByEmail(user.getEmail());
            if (existsUser != null) {
                throw new IllegalArgumentException(
                    "User with email '" + existsUser.getEmail() + "' already exists"
                );
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Logger.info("User was added:\n" + user);
            return userRepository.save(user);
        } catch (IllegalArgumentException | DataAccessException exception) {
            Logger.warn("User wasn't added: " + user + "\n" + exception.getMessage());
            return null;
        }
    }


    public User updateUserById(User user, int id) {
        try {
            User userToUpdate = getUserById(id);
            if (userToUpdate == null) {
                throw new IllegalArgumentException("User with id '" + id + "' not found");
            }
            validator.validate(user);

            user.setId(id);
            if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            } else {
                user.setPassword(userToUpdate.getPassword());
                userRepository.save(user);
            }
            Logger.info("User was updated:\n" + user);
            return getUserById(id);
        } catch (IllegalArgumentException | DataAccessException exception) {
            Logger.warn("User wasn't updated: " + user + "\n" + exception.getMessage());
            return null;
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElse(null);
    }
}
