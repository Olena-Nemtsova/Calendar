package edu.step.model;

import edu.step.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventValidator {
    private final UserRepository userRepository;

    public void validate(Event event) {
        validateDate(event.getDate());
        validateDescription(event.getDescription());
        validateUserId(event.getUserId());
    }

    private void validateDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Event date was null");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Event description was null or empty");
        }
    }

    private void validateUserId(int userId) {
        if (userRepository.findById(userId).orElse(null) == null) {
            throw new IllegalArgumentException("Event had not exists user id");
        }
    }
}
