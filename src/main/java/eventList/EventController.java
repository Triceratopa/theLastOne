package eventList;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> findAllEvents() {
        return this.eventService.findAllEvents();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventResponse findEventById(@PathVariable Long id) {
        return this.eventService.findEventById(id);
    }
    @GetMapping("/myEvents")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public List<EventResponse> findMyEvents() {
        return this.eventService.findMyEvents();
    }
    @GetMapping("/subEvents")
    @ResponseStatus(HttpStatus.OK)

    public List<EventResponse> findSubEvents() {
        return this.eventService.findSubEvents();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ORGANIZER')")
    public EventResponse createEvent(@RequestBody EventRequest eventRequest) {
        return this.eventService.createEvent(eventRequest);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public EventResponse updateEvent(@PathVariable Long id, @RequestBody EventRequest eventRequest) {
        return this.eventService.updateEvent(id, eventRequest);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ORGANIZER')")
    public void deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEvent(id);
    }

}
