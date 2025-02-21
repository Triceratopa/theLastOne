package eventList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String name;
    private String description;
    private String location;
    private String date;
    private int capacity;
    private Long organizerId;
}
