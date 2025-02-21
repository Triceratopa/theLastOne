package eventList;

import auth.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private long id;
    private String name;
    private String description;
    private String location;
    private String date;
    private int capacity;
    private AppUser organizer;
private List<AppUser> participants=new ArrayList<>();


}
