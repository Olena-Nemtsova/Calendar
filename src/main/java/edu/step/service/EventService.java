package edu.step.service;

import edu.step.model.Event;
import edu.step.model.EventValidator;
import edu.step.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventValidator validator;

    public Event addEvent(Event event) {
        try {
            validator.validate(event);

            Logger.info("Event was added:\n" + event);
            return eventRepository.save(event);
        } catch (IllegalArgumentException | DataAccessException exception) {
            Logger.warn("Event wasn't added: " + event + "\n" + exception.getMessage());
            return null;
        }
    }

    public boolean deleteEventById(int id) {
        Event eventToDel = getEventById(id);
        try {
            if (eventToDel == null) {
                throw new IllegalArgumentException("Event with id '" + id + "' not found");
            }
            eventRepository.deleteById(id);

            Logger.info("Event was deleted:\n" + eventToDel);
            return true;
        } catch (IllegalArgumentException | DataAccessException exception) {
            Logger.warn("Event wasn't deleted: " + eventToDel + "\n" + exception.getMessage());
            return false;
        }
    }

    public List<Event> getUserEventsByDate(int userId, LocalDateTime date) {
        return eventRepository.findByUserIdAndDate(userId, date);
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Integer> getUserEventsOfMonth(int userId, LocalDateTime date) {
        return eventRepository
            .findByUserIdAndDate(userId, date.getMonthValue(), date.getYear()).stream()
            .map(event -> event.getDate().getDayOfMonth())
            .toList();
    }
}
