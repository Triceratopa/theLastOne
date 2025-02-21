package eventList;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @NotBlank(message = "Name is required")
     String name;
    @NotBlank(message = "Description is required")
     String description;
    @NotBlank(message = "Location is required")
     String location;
    @NotBlank(message = "Date is required")
    LocalDate date;
    @NotBlank(message = "Capacity is required")
     int capacity;

    Long organizerId;
}
