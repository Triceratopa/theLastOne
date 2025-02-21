package eventList;

import auth.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class EventService {
    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;
    Event event = new Event();
    BeanUtils.copyProperties(eventRequest, event);
    event.setOrganizer(appUserRepository.findById(event.getOrganizer().getId()).get());
}
