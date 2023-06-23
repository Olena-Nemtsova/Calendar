package edu.step.model;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validate(User user) {
        validateUserIsNotNull(user);
        validateName(user.getFirstName(), "firstName");
        validateName(user.getLastName(), "lastName");
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
    }

    private void validateUserIsNotNull(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User was null");
        }
    }

    private void validateName(String name, String propertyName) {
        if (name == null) {
            throw new IllegalArgumentException("User " + propertyName + " was null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("User " + propertyName + " was empty");
        }
        if (name.length() > 100 || name.length() < 2) {
            throw new IllegalArgumentException("User " + propertyName + " had wrong length");
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("User email was null");
        }
        if (email.isBlank()) {
            throw new IllegalArgumentException("User email was empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("User email not match email pattern");
        }
        if (email.length() > 250 || email.length() < 9) {
            throw new IllegalArgumentException("User email had wrong length");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("User password was null");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("User password was empty");
        }
    }
}
