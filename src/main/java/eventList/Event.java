package eventList;

import auth.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;
    private String location;
    private LocalDate date;
    private int capacity;
    @ManyToOne
    private AppUser organizer;
    @OneToMany
    private List<AppUser> attend = new ArrayList<>();
}
