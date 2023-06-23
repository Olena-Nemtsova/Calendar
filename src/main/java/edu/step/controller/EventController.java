package edu.step.controller;

import edu.step.model.Event;
import edu.step.security.SecurityUser;
import edu.step.service.EventService;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;

    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        event.setUserId(getUserId());
        Event added = service.addEvent(event);
        if (added == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id) {
        if (service.deleteEventById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<Collection<Event>> getUserEventsByDate(@RequestBody Event dateDto) {
        return ResponseEntity.ok(service.getUserEventsByDate(getUserId(), dateDto.getDate()));
    }

    @PutMapping("/month")
    public ResponseEntity<Collection<Integer>> getUserEventsOfMonth(@RequestBody Event dateDto) {
        return ResponseEntity.ok(service.getUserEventsOfMonth(getUserId(), dateDto.getDate()));
    }

    private int getUserId() {
        Object object = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        SecurityUser user = (SecurityUser) object;
        return user.getUserId();
    }
}
