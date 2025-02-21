package eventList;

import auth.AppUser;
import auth.AppUserRepository;
import auth.AppUserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service

@Validated
public class EventService {
    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

public EventService (EventRepository eventRepository, AppUserRepository appUserRepository, AppUserService appUserService) {
    this.eventRepository = eventRepository;
    this.appUserRepository = appUserRepository;
    this.appUserService = appUserService;

}
public EventResponse eventResponseFromEvent(Event event) {
    EventResponse eventResponse = new EventResponse();
    BeanUtils.copyProperties(event, eventResponse);
    eventResponse.setOrganizer(appUserRepository.findById(event.getOrganizer().getId()).get());
    eventResponse.setParticipants(event.getParticipants().stream().map(participant -> appUserRepository.findById(participant.getId()).get()).collect(Collectors.toList()));
    return eventResponse;
}
public List<EventResponse> EventResponseListFromEventList(List<Event> events) {
    return events.stream().map(this::eventResponseFromEvent).toList(); }



    public List<EventResponse> findAll() {
    return EventResponseListFromEventList(eventRepository.findAll());}

public EventResponse findById(Long id) {
    return eventResponseFromEvent(eventRepository.findById(id).get());
}
public Event eventFromEventRequest(@Valid EventRequest eventRequest) {
    Event event = new Event();
    BeanUtils.copyProperties(eventRequest, event);
    return event;
}

public EventResponse save(@Valid EventRequest eventRequest) {
    if (eventRepository.existsByName(eventRequest.getName())) {
        throw new EntityExistsException("Event exists");
    }
    Event event = eventFromEventRequest(eventRequest);
    event.setOrganizer(appUserRepository.findById(getCurrentUserId()).get());
    eventRepository.save(event);
    return new EventResponse();
}

public EventResponse update(Long id, @Valid EventRequest eventRequest) {
    Event event = eventFromEventRequest(eventRequest);
    if (!Objects.equals(eventRepository.findById(id).get().getOrganizer().getId(), appUserService.getThisUserId())) {
        throw new SecurityException("You are not the organizer of this event");
    }

    event.setOrganizer(appUserRepository.findById(getCurrentUserId()).get());
    event.setParticipants(eventRepository.findById(id).get().getParticipants());
    event.setId(id);
    return eventResponseFromEvent(eventRepository.save(event));
}



public void delete(Long id) {
    Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    if (!Objects.equals(event.getOrganizer().getId(), getCurrentUserId())) {
        throw new SecurityException("You are not the organizer of this event");
    }
    eventRepository.deleteById(id);
}

public EventResponse addParticipant(Long eventId) {
    Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    if (event.getParticipants().contains(appUserRepository.findById(getCurrentUserId()).get())) {
        throw new EntityExistsException("You are a participant of this event");
    }
    if (event.getParticipants().size() >= event.getCapacity()) {
        throw new IllegalStateException("Event complete");
    }
    event.getParticipants().add(appUserRepository.findById(getCurrentUserId()).get());
    return eventResponseFromEvent(eventRepository.save(event));
}

public EventResponse removeParticipant(Long eventId) {
    Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));

    if (!event.getParticipants().contains(appUserRepository.findById(getCurrentUserId()).get())) {
        throw new EntityNotFoundException("You are not a participant of this event");
    }
    event.getParticipants().remove(appUserRepository.findById(getCurrentUserId()).get());
    return eventResponseFromEvent(eventRepository.save(event));
}

public List<EventResponse> findAllEventsByUserId() {
    return this.eventRepository.findAll().stream().filter(
            event -> event.getParticipants().stream().anyMatch(
                    participant -> Objects.equals(participant.getId(), this.getCurrentUserId())
            )
    ).map(this::eventResponseFromEvent).toList();
}

public List<EventResponse> findAllEventsByOrganizer() {
    return EventResponseListFromEventList(eventRepository.findAllByOrganizerId(getCurrentUserId()));
}

public Long getCurrentUserId() {
    return appUserService.getThisUserId();
}}