package eventList;

import org.springframework.beans.BeanUtils;

public class EventService {
    Event event = new Event();
    BeanUtils.copyProperties(eventRequest, event);
    event.setOrganizer(appUserRepository.findById(event.getOrganizer().getId()).get());
}
